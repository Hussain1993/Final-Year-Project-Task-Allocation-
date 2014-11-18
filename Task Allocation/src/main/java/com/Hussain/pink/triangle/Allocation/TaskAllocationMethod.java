package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hussain on 14/11/2014.
 */
public abstract class TaskAllocationMethod {
    private StringBuilder employeeQuery = new StringBuilder("");
    private StringBuilder taskQuery = new StringBuilder("");

    protected static final Logger LOG = LoggerFactory.getLogger(TaskAllocationMethod.class);
    protected Graph<Employee,Task> allocationGraph;

    public static final int ORDER_NAME_ALPHABETICAL = 1;
    public static final int ORDER_COST_LOW_TO_HIGH = 2;
    public static final int ORDER_COST_HIGH_TO_LOW = 3;

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

                Set<Skill> skillSet = buildSkillSet(skills,proficiency);

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

                Set<Skill> skillSet = buildSkillSet(skills,proficiencyRequired);

                LOG.debug("Adding the task with the name {} to the graph",taskName);
                allocationGraph.addTaskNode(new Task(id,taskName,projectID,dateFrom.getTime(),dateTo.getTime(),completed,skillSet));
            }
        }
        catch(SQLException e){
            LOG.error("There was an error with the SQL Result Set",e);
        }
        finally {
            DbUtils.closeQuietly(employeeResults);
            DbUtils.closeQuietly(taskResults);
        }
        return null;
    }

    public boolean checkSkillsMatch(Employee employee, Task task){
        Set<Skill> employeeSkills = employee.getSkills();
        Set<Skill> taskSkills = task.getSkills();
        return CollectionUtils.isEqualCollection(employeeSkills,taskSkills);
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

    public ResultSet executeQuery(String query){
        return null;
    }

    private Set<Skill> buildSkillSet(String skillResult, String proficiencyResult){
        Set<Skill> skillSet = new HashSet<>();
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
