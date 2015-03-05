package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.*;
import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This abstract class will be used to query the database,
 * to find all the employees and tasks within the database and
 * build a graph out of the data that has been returned from the database.
 *
 * Subclasses should provide their own implementations of the
 * allocateTasks method
 *
 * Created by Hussain on 14/11/2014.
 */
public abstract class TaskAllocationMethod {
    protected static final Logger LOG = LoggerFactory.getLogger(TaskAllocationMethod.class);

    protected Matching<String> matching = new Matching<>();
    protected Set<String> unmatchedEmployees;
    protected Set<String> unmatchedTasks;

    //This is the generic SQL query that will be used to get the employees and their skills from the database,
    //This query will not check if the employee has been assigned to a task previously
    private StringBuilder genericEmployeeQuery = new StringBuilder("select employees.id,concat_ws(' ',employees.first_name,employees.last_name) as name,group_concat(skills.skill),\n" +
            " employees.cost, group_concat(employee_skills.PROFICIENCY)\n" +
            " from employee_skills join employees on employee_skills.employee_id = employees.id\n" +
            " join skills on employee_skills.skill_id = skills.id group by employees.id");

    //SQL query to get employees and skills from the database and also checking if they have any
    //tasks assigned to them
    private StringBuilder employeeAssignedToTasksQuery = new StringBuilder("SELECT EMPLOYEES.ID, " +
            "CONCAT_WS(' ',EMPLOYEES.FIRST_NAME,EMPLOYEES.LAST_NAME) AS NAME, GROUP_CONCAT(SKILLS.SKILL), " +
            "EMPLOYEES.COST, GROUP_CONCAT(EMPLOYEE_SKILLS.PROFICIENCY), ASSIGNED_TO.TASK_ID, TASKS.DATE_FROM, " +
            "TASKS.DATE_TO, TASKS.COMPLETED  FROM EMPLOYEE_SKILLS JOIN EMPLOYEES ON EMPLOYEE_SKILLS.EMPLOYEE_ID = EMPLOYEES.ID " +
            "JOIN SKILLS ON EMPLOYEE_SKILLS.SKILL_ID = SKILLS.ID " +
            "LEFT JOIN ASSIGNED_TO ON EMPLOYEES.ID = ASSIGNED_TO.EMPLOYEE_ID " +
            "LEFT JOIN TASKS ON ASSIGNED_TO.TASK_ID = TASKS.ID GROUP BY EMPLOYEES.ID");

    private StringBuilder employeeQuery;


    //SQL query to get the tasks and skills within the database
    private StringBuilder taskQuery = new StringBuilder("SELECT TASKS.ID, TASKS.NAME, TASKS.PROJECT_ID, " +
            "TASKS.DATE_FROM, TASKS.DATE_TO, TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), " +
            "GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED), PROJECTS.NAME FROM TASKS, SKILLS, TASK_SKILLS, PROJECTS  " +
            "WHERE TASKS.ID = TASK_SKILLS.TASK_ID AND SKILLS.ID = TASK_SKILLS.SKILL_ID " +
            "AND TASKS.PROJECT_ID = PROJECTS.ID");

    private Connection conn;
    private Statement stmt;

    public static final String ORDER_NAME_ALPHABETICAL = " order by name asc";
    public static final String ORDER_NAME_REVERSE_ALPHABETICAL = " order by name desc";
    public static final String ORDER_COST_LOW_TO_HIGH = " order by cost asc";
    public static final String ORDER_COST_HIGH_TO_LOW = " order by cost desc";

    private static final String GROUP_BY_PROJECTS = " group by projects.id";


    public static final int EMPLOYEE_QUERY = 4;
    public static final int TASK_QUERY = 5;

    /**
     * This is the method that will need to be implemented
     * by any of its subclasses, they should provide
     * their own matching algorithm
     * @param biPartiteGraph A graph to hold information
     * @return A matching of employees to tasks
     */
    public abstract Matching<String> allocateTasks(BiPartiteGraph biPartiteGraph);

    /**
     * This is takes the employee and task result from the database
     * and builds a new bipartite graph
     * @param employeeResults This is the employee result set that is returned from the database
     * @param taskResults This is the task result set that is returned from the database
     * @return A new bipartite to be used later
     */
    public BiPartiteGraph buildGraph(ResultSet employeeResults, ResultSet taskResults){
        BiPartiteGraph biPartiteGraph = new BiPartiteGraph();
        try{
            //Loop over every employee from the database
            while (employeeResults != null && employeeResults.next())
            {
                Employee employee;
                int id = employeeResults.getInt(1);
                String name = employeeResults.getString(2);
                String skills = employeeResults.getString(3);
                int cost = employeeResults.getInt(4);
                String proficiency = employeeResults.getString(5);

                LinkedHashSet<Skill> skillSet = buildSkillSet(skills,proficiency);
                if(skillSet == null)//Sanity check, this should never happen
                {
                    LOG.error("There was an error when building the skill set for the" +
                            "employee {}", name);
                    continue;//Move onto the next employee from the results
                }
                ResultSetMetaData resultSetMetaData = employeeResults.getMetaData();
                //This is the number of columns that there will be when there is a
                //employee query checking if they have been assigned to a task we are also checking
                //if there is a TASK ID for this row
                if(resultSetMetaData.getColumnCount() == 9 && !checkIfColumnIsNull(employeeResults,"TASK_ID"))
                {
                    //This is a task that has an employee assigned to them
                    int taskID = employeeResults.getInt(6);
                    Date dateFrom = employeeResults.getDate(7);
                    Date dateTo = employeeResults.getDate(8);
                    boolean taskStatus = employeeResults.getBoolean(9);

                    Task assignedTask = new Task(taskID,"Task Assigned To Employee",null,dateFrom.getTime(),dateTo.getTime(),taskStatus,null);

                    LOG.debug("The employee {} has been assigned a previous task of ID {}",name,taskID);
                    employee = new Employee(id,name,skillSet,cost,assignedTask);
                }
                else
                {
                    //This is an employee that has not been assigned a task, or the user has chosen to not check if
                    //employees have been assigned to a task
                    employee = new Employee(id,name,skillSet,cost);
                }
                LOG.debug("Adding the employee with the name {} to the graph",name);
                biPartiteGraph.addEmployeeToIndexMap(employee);
            }
            //Loop over every task within the database
            while (taskResults != null && taskResults.next())
            {
                int taskID = taskResults.getInt(1);
                String taskName = taskResults.getString(2);
                int projectID = taskResults.getInt(3);
                Date dateFrom = taskResults.getDate(4);
                Date dateTo = taskResults.getDate(5);
                boolean completed = taskResults.getBoolean(6);
                String skills = taskResults.getString(7);
                String proficiencyRequired = taskResults.getString(8);

                LinkedHashSet<Skill> skillSet = buildSkillSet(skills,proficiencyRequired);//Build the skill set for the task

                if(skillSet == null)//Sanity check, this should never happen
                {
                    LOG.error("There was an error when building the skill set for the" +
                            "task {}", taskName);
                    continue;//Move onto the next task from the results
                }

                String projectName = taskResults.getString(9);
                Project project = new Project(projectID,projectName);
                if(AdvancedOptions.groupTasksByProject())
                {
                    GroupTask.addNewTaskToGroup(project);
                }
                LOG.debug("Adding the task with the name {} to the graph",taskName);
                biPartiteGraph.addTaskToIndexMap(new Task(taskID,taskName,project,dateFrom.getTime(),dateTo.getTime(),completed,skillSet));
            }
        }
        catch (SQLException e){
            LOG.error("There was an error with the SQL Statement");
        }
        finally {
            DbUtils.closeQuietly(conn,stmt,employeeResults);
            DbUtils.closeQuietly(taskResults);
        }
        return buildGraph(biPartiteGraph);
    }

    /**
     * This build all the allocations within the graph
     * @param biPartiteGraph This is a bipartite graph with the employee nodes and task nodes with no allocations between them
     * @return A bipartite graph with all the allocations added in
     */
    private BiPartiteGraph buildGraph(BiPartiteGraph biPartiteGraph){
        for (String employeeName : biPartiteGraph.getEmployeeNodes())
        {
            Employee employee = biPartiteGraph.getEmployeeByName(employeeName);
            for (String taskName : biPartiteGraph.getTaskNodes())
            {
                Task task = biPartiteGraph.getTaskByName(taskName);
                boolean employeeAvailableForTask = checkEmployeeAvailableForTask(employee,task);
                boolean skillsMatch = checkSkillsMatch(employee,task);
                if(skillsMatch && employeeAvailableForTask)
                {
                    //When there is a matching do not move onto the next employee
                    biPartiteGraph.addEdge(employee,task);
                }
            }
        }
        return biPartiteGraph;
    }

    /**
     * Checks to see if a column in a SQL ResultSet
     * is null
     * @param resultSet The result set to check
     * @param columnName The name of the column to check
     * @return True if the column is null, false otherwise
     * @throws SQLException When there is an error with the result set
     * for example when it has been closed
     */
    private boolean checkIfColumnIsNull(ResultSet resultSet, String columnName) throws SQLException{
        resultSet.getInt(columnName);
        return resultSet.wasNull();
    }

    /**
     * Checks to see if the employee skills match
     * the skills that are required by the task
     * @param employee The selected employee to check if the skills match
     * @param task The task that has been chosen
     * @return True if the skills match, false otherwise
     */
    public boolean checkSkillsMatch(Employee employee, Task task){
        LinkedHashSet<Skill> taskSkills = task.getSkills();
        if(taskSkills.size() > employee.getSkills().size())
        {
            //The task requires more skills than the employee knows
            return false;
        }
        for (Skill taskSkill : taskSkills) {
            if(!checkSkillsMatch(taskSkill,employee))
            {
                return false;
            }
        }
        return true;
    }

    /**
     * This method takes the chosen task skill and loops over
     * the skills for the employee and check to see if they match,
     *
     * This method also takes proficiency into account
     * @param taskSkill The selected task skill that should be checked.
     * @param employee The employee to check if the skills match
     * @return true if the skills match, false otherwise
     */
    private boolean checkSkillsMatch(Skill taskSkill, Employee employee){
        LinkedHashSet<Skill> employeeSkills = employee.getSkills();
        for (Skill employeeSkill : employeeSkills) {
            if(taskSkill.equals(employeeSkill))
            {
                return true;
            }
            else if(taskSkill.getSkill().equals(employeeSkill.getSkill()) && employeeSkill.getProficiency() >= taskSkill.getProficiency())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks to see if an employee is available
     * for a task given that they already have a task assigned to them,
     * this method will check will check if the start date and end date of the
     * new task and assigned task clash.
     * @param employee The purposed employee for the task
     * @param task The task to check
     * @return True if the employee is available for the task, false otherwise
     */
    public boolean checkEmployeeAvailableForTask(Employee employee, Task task){
        //Return true if the employee has not been assigned a task
        if(employee.getEmployeeType().equals(EmployeeType.NOT_ASSIGNED_TASK))
        {
            return true;
        }
        Task taskAssignedToEmployee = employee.getTaskAssigned();

        //Return true if the employee has completed the task that they have been assigned
        if(taskAssignedToEmployee.isCompleted())
        {
            return true;
        }
        LocalDate employeeTaskStartDate = taskAssignedToEmployee.getDateFrom();
        LocalDate employeeTaskEndDate = taskAssignedToEmployee.getDateTo();

        LocalDate startDate = task.getDateFrom();
        LocalDate endDate = task.getDateTo();

        Interval employeeRange = new Interval(employeeTaskStartDate.toDate().getTime(),
                employeeTaskEndDate.toDate().getTime());
        Interval taskRange = new Interval(startDate.toDate().getTime(), endDate.toDate().getTime());

        return !taskRange.overlaps(employeeRange);
    }

    /**
     * Sets whether the task query should group the
     * tasks by project
     * @param groupTasksByProjects true if you would like
     *                             group the tasks by the projects or
     *                             false if you don't.
     */
    public void setTaskGroupOrder(boolean groupTasksByProjects){
        if(groupTasksByProjects)
        {
            taskQuery.append(GROUP_BY_PROJECTS + ", tasks.id");
        }
        else
        {
            taskQuery.append(" group by tasks.id");
        }
    }

    /**
     * Sets the query order for the employee and task query
     * @param order This is the order that should be used for the
     *              query either alphabetical etc...
     * @param query For which query to set the order for,
     *              either the employee query or the task query
     */
    public void setQueryOrder(String order, int query){
        switch (query)
        {
            case EMPLOYEE_QUERY: employeeQuery.append(order);break;
            case TASK_QUERY: taskQuery.append(order); break;
            default: LOG.error("The parameter {} was not recognised",query);
        }
    }

    public StringBuilder getEmployeeQuery() {
        return employeeQuery;
    }

    /**
     * This method sets the employee query depending on
     * what options the user has chosen
     * @param checkIfEmployeesAreAssignedToTasks True if you would
     *                                           like to check if
     *                                           the employee has
     *                                           been assigned to a task
     */
    public void setEmployeeQuery(boolean checkIfEmployeesAreAssignedToTasks){
        if(checkIfEmployeesAreAssignedToTasks)
        {
            employeeQuery = employeeAssignedToTasksQuery;
        }
        else
        {
            employeeQuery = genericEmployeeQuery;
        }
    }

    public StringBuilder getTaskQuery() {
        return taskQuery;
    }

    /**
     * This method execute the relevant SQL query
     * @param query an integer representing which
     *              query should be executed:
     *              4 for the employee query
     *              5 for the task query
     * @return a SQL ResultSet containing the results returned from
     * executing the query
     */
    public ResultSet executeQuery(int query){
        switch (query)
        {
            case EMPLOYEE_QUERY: return executeQuery(getEmployeeQuery().toString());
            case TASK_QUERY: return executeQuery(getTaskQuery().toString());
            default: break;
        }
        return null;
    }

    /**
     * Executes the query
     * @param query String representation of the query
     * @return a SQL ResultSet containing the results returned from
     * executing the query
     */
    private ResultSet executeQuery(String query){
        conn = DatabaseConnection.getDatabaseConnection();
        try{
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        }
        catch (SQLException e) {
            LOG.error("There was an error with the SQL Statement {}",query,e);
        }
        //Do not close the resources just yet as we use them later on
        return null;
    }

    /**
     * Builds the skill set for the employee and tasks
     * @param skillResult The skills that have been returned from the database
     * @param proficiencyResult The proficiency returned from the database
     * @return A set containing the skill set for either the chosen employee
     * or task
     */
    public LinkedHashSet<Skill> buildSkillSet(String skillResult, String proficiencyResult){
        LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();
        String [] skillArray = skillResult.split(",");
        String [] proficiencyArray = proficiencyResult.split(",");
        if(skillArray.length != proficiencyArray.length)
        {
            return null;
        }
        for (int i = 0; i < skillArray.length; i++) {
            String skill = skillArray[i];
            int proficiency = Integer.parseInt(proficiencyArray[i]);
            skillSet.add(new Skill(skill,proficiency));
        }
        return skillSet;
    }

    /**
     * This method logs and shows all the
     * unmatched employees and tasks after the
     * allocation has finished
     */
    protected void logUnmatchedEmployeesAndTasks(){
        if(unmatchedEmployees.size() == 0)
        {
            LOG.info("All employees within the database have been assigned to a task");
        }
        else
        {
            LOG.info("The set of unmatched employees {}",unmatchedEmployees);
        }
        if(unmatchedTasks.size() == 0)
        {
            LOG.info("All tasks have been assigned to a employee");
        }
        else
        {
            LOG.info("The set of unmatched tasks {}",unmatchedTasks);
        }
    }
}
