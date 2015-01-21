package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.EmployeeType;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedHashSet;

/**
 * Created by Hussain on 14/11/2014.
 */
public abstract class TaskAllocationMethod {
    private StringBuilder genericEmployeeQuery = new StringBuilder("select employees.id,concat_ws(' ',employees.first_name,employees.last_name) as name,group_concat(skills.skill),\n" +
            " employees.cost, group_concat(employee_skills.PROFICIENCY)\n" +
            " from employee_skills join employees on employee_skills.employee_id = employees.id\n" +
            " join skills on employee_skills.skill_id = skills.id group by employees.id");
    private StringBuilder employeeAssignedToTasksQuery = new StringBuilder("SELECT EMPLOYEES.ID, " +
            "CONCAT_WS(' ',EMPLOYEES.FIRST_NAME,EMPLOYEES.LAST_NAME) AS NAME, GROUP_CONCAT(SKILLS.SKILL), " +
            "EMPLOYEES.COST, GROUP_CONCAT(EMPLOYEE_SKILLS.PROFICIENCY), ASSIGNED_TO.TASK_ID, TASKS.DATE_FROM, " +
            "TASKS.DATE_TO, TASKS.COMPLETED  FROM EMPLOYEE_SKILLS JOIN EMPLOYEES ON EMPLOYEE_SKILLS.EMPLOYEE_ID = EMPLOYEES.ID " +
            "JOIN SKILLS ON EMPLOYEE_SKILLS.SKILL_ID = SKILLS.ID " +
            "LEFT JOIN ASSIGNED_TO ON EMPLOYEES.ID = ASSIGNED_TO.EMPLOYEE_ID " +
            "LEFT JOIN TASKS ON ASSIGNED_TO.TASK_ID = TASKS.ID GROUP BY EMPLOYEES.ID");

    private StringBuilder employeeQuery;

    private StringBuilder taskQuery = new StringBuilder("SELECT TASKS.ID, TASKS.NAME, TASKS.PROJECT_ID, " +
            "TASKS.DATE_FROM, TASKS.DATE_TO, TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), " +
            "GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED) FROM TASKS, SKILLS, TASK_SKILLS, PROJECTS  " +
            "WHERE TASKS.ID = TASK_SKILLS.TASK_ID AND SKILLS.ID = TASK_SKILLS.SKILL_ID " +
            "AND TASKS.PROJECT_ID = PROJECTS.ID");

    private Connection conn;
    private Statement stmt;

    protected static final Logger LOG = LoggerFactory.getLogger(TaskAllocationMethod.class);
    protected Graph<Employee,Task> allocationGraph;

    public static final String ORDER_NAME_ALPHABETICAL = " order by name asc";
    public static final String ORDER_NAME_REVERSE_ALPHABETICAL = " order by name desc";
    public static final String ORDER_COST_LOW_TO_HIGH = " order by cost asc";
    public static final String ORDER_COST_HIGH_TO_LOW = " order by cost desc";

    private static final String GROUP_BY_PROJECTS = " group by projects.id";


    public static final int EMPLOYEE_QUERY = 4;
    public static final int TASK_QUERY = 5;

    public abstract void allocateTasks(Graph<Employee,Task> allocationGraph);

    public Graph<Employee,Task> buildGraph(ResultSet employeeResults, ResultSet taskResults){
        allocationGraph = new Graph<>();
        try{
            while(employeeResults != null && employeeResults.next())
            {
                Employee e;
                int id = employeeResults.getInt(1);
                String name = employeeResults.getString(2);
                String skills = employeeResults.getString(3);
                int cost = employeeResults.getInt(4);
                String proficiency = employeeResults.getString(5);

                LinkedHashSet<Skill> skillSet = buildSkillSet(skills,proficiency);

                if(skillSet == null)
                {
                    LOG.error("There was an error when building the skill set for the" +
                            "employee {}", name);
                }

                ResultSetMetaData resultSetMetaData = employeeResults.getMetaData();
                //This is the number of columns that there will be when there is a
                //employee query checking if they have been assigned to a task we are also checking
                //if there is a TASK ID for this row
                if(resultSetMetaData.getColumnCount() == 9 && !checkIfColumnIsNull(employeeResults,"TASK_ID"))
                {
                    //This is an employee that has a task assigned to them
                    int taskID = employeeResults.getInt(6);
                    Date dateFrom = employeeResults.getDate(7);
                    Date dateTo = employeeResults.getDate(8);
                    boolean taskStatus = employeeResults.getBoolean(9);

                    Task assignedTask = new Task(taskID,"Task Assigned To Employee",-1,dateFrom.getTime(),dateTo.getTime(),taskStatus,null);

                    e = new Employee(id,name,skillSet,cost,assignedTask);

                }
                else
                {
                    //This is an employee that has not been assigned a task
                    e = new Employee(id,name,skillSet,cost);
                }

                LOG.debug("Adding the employee with the name {} to the graph",name);
                allocationGraph.addEmployeeNode(e);
            }
            while(taskResults != null &&taskResults.next())
            {
                int id = taskResults.getInt(1);
                String taskName = taskResults.getString(2);
                int projectID = taskResults.getInt(3);
                Date dateFrom = taskResults.getDate(4);
                Date dateTo = taskResults.getDate(5);
                boolean completed = taskResults.getBoolean(6);

                String skills = taskResults.getString(7);
                String proficiencyRequired = taskResults.getString(8);

                LinkedHashSet<Skill> skillSet = buildSkillSet(skills,proficiencyRequired);

                LOG.debug("Adding the task with the name {} to the graph",taskName);
                allocationGraph.addTaskNode(new Task(id,taskName,projectID,dateFrom.getTime(),dateTo.getTime(),completed,skillSet));
            }
        }
        catch(SQLException e){
            LOG.error("There was an error with the SQL Result Set",e);
        }
        finally {
            DbUtils.closeQuietly(conn,stmt,employeeResults);
            DbUtils.closeQuietly(taskResults);
        }
        return allocationGraph;
    }

    private boolean checkIfColumnIsNull(ResultSet resultSet, String columnName) throws SQLException{
        resultSet.getInt(columnName);
        return resultSet.wasNull();
    }

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

    public boolean checkEmployeeAvailableForTask(Employee employee, Task task){
        //Return true if the employee has not been assigned a task
        if(employee.getEmployeeType().equals(EmployeeType.NOT_ASSIGNED_TASK))
        {
            return true;
        }
        Task taskAssignedToEmployee = employee.getTaskAssigned();

        //Return true if the employee has completed the task
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

    public ResultSet executeQuery(int query){
        switch (query)
        {
            case EMPLOYEE_QUERY: return executeQuery(getEmployeeQuery().toString());
            case TASK_QUERY: return executeQuery(getTaskQuery().toString());
        }
        return null;
    }

    private ResultSet executeQuery(String query){
        conn = DatabaseConnection.getDatabaseConnection();
        try{
            stmt = conn.createStatement();
            return stmt.executeQuery(query);
        }
        catch (SQLException e) {
            LOG.error("There was an error with the SQL Statement {}",query,e);
        }
        return null;
    }

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
}
