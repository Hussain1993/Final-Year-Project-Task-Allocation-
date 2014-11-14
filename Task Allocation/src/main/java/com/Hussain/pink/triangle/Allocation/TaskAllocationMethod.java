package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;

/**
 * Created by Hussain on 14/11/2014.
 */
public abstract class TaskAllocationMethod {
    private StringBuilder employeeQuery = new StringBuilder("");
    private StringBuilder taskQuery = new StringBuilder("");

    protected static final Logger LOG = LoggerFactory.getLogger(TaskAllocationMethod.class);

    public static final int ORDER_NAME_ALPHABETICAL = 1;
    public static final int ORDER_COST_LOW_TO_HIGH = 2;
    public static final int ORDER_COST_HIGH_TO_LOW = 3;

    public static final int EMPLOYEE_QUERY = 4;
    public static final int TASK_QUERY = 5;

    public abstract void allocateTasks(Graph<Employee,Task> allocationGraph);

    public abstract void setOrder(int order);

    public Graph buildGraph(ResultSet employeeResults, ResultSet taskResults){
        return null;
    }

    public boolean checkSkillsMatch(Employee employee, Task task){
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

    public ResultSet executeQuery(String query){
        return null;
    }
}
