package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedHashSet;

/**
 * Created by Hussain on 14/11/2014.
 */
public abstract class TaskAllocationMethod {
    private StringBuilder employeeQuery = new StringBuilder("select employees.id,concat_ws(' ',employees.first_name,employees.last_name) as name,group_concat(skills.skill),\n" +
            " employees.cost, group_concat(employee_skills.PROFICIENCY)\n" +
            " from employee_skills join employees on employee_skills.employee_id = employees.id\n" +
            " join skills on employee_skills.skill_id = skills.id group by employees.id");
    private StringBuilder taskQuery = new StringBuilder("select tasks.id, tasks.name,tasks.project_id,tasks.date_from,tasks.date_to,tasks.completed,\n" +
            " group_concat(skills.skill), group_concat(task_skills.proficiency_required)\n" +
            " from task_skills join tasks on task_skills.task_id = tasks.id\n" +
            " join skills on task_skills.skill_id=skills.id\n" +
            " join TaskAllocation.projects on tasks.project_id=projects.id group by tasks.id");

    private Connection conn;
    private Statement stmt;

    protected static final Logger LOG = LoggerFactory.getLogger(TaskAllocationMethod.class);
    protected Graph<Employee,Task> allocationGraph;

    public static final String ORDER_NAME_ALPHABETICAL = " order by name asc";
    public static final String ORDER_COST_LOW_TO_HIGH = " order by cost asc";
    public static final String ORDER_COST_HIGH_TO_LOW = " order by cost desc";


    public static final int EMPLOYEE_QUERY = 4;
    public static final int TASK_QUERY = 5;

    public abstract void allocateTasks(Graph<Employee,Task> allocationGraph);

    public Graph<Employee,Task> buildGraph(ResultSet employeeResults, ResultSet taskResults){
        allocationGraph = new Graph<>();
        try{
            while(employeeResults.next())
            {
                int id = employeeResults.getInt(1);
                String name = employeeResults.getString(2);
                String skills = employeeResults.getString(3);
                int cost = employeeResults.getInt(4);
                String proficiency = employeeResults.getString(5);

                LinkedHashSet<Skill> skillSet = buildSkillSet(skills,proficiency);

                LOG.debug("Adding the employee with the name {} to the graph",name);
                allocationGraph.addEmployeeNode(new Employee(id,name,skillSet,cost));
            }
            while(taskResults.next())
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

    public boolean checkSkillsMatch(Employee employee, Task task){
        LinkedHashSet<Skill> taskSkills = task.getSkills();
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
        return false;
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

    private LinkedHashSet<Skill> buildSkillSet(String skillResult, String proficiencyResult){
        LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();
        String [] skillArray = skillResult.split(",");
        String [] proficiencyArray = proficiencyResult.split(",");
        for (int i = 0; i < skillArray.length; i++) {
            String skill = skillArray[i];
            int proficiency = Integer.parseInt(proficiencyArray[i]);
            skillSet.add(new Skill(skill,proficiency));
        }
        return skillSet;
    }
}
