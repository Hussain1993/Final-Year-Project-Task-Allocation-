package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Allocation.GreedyTaskAllocation;
import com.Hussain.pink.triangle.Allocation.TaskAllocationMethod;
import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hussain on 03/12/2014.
 */
public class Allocation {
    private TaskAllocationMethod taskAllocationMethod;

    public Allocation(int algorithm){
        switch(algorithm)
        {
            case 0: taskAllocationMethod = new GreedyTaskAllocation();break;
            //There will be the option for the maximum algorithm here
            default: taskAllocationMethod = new GreedyTaskAllocation(); break;
        }
    }

    public ArrayList<Object[]> allocateEmployeesAndTasks(){
        taskAllocationMethod.setEmployeeQuery(AdvancedOptions.checkIfEmployeesAreAssignedToTasks());
        taskAllocationMethod.setTaskGroupOrder(AdvancedOptions.groupTasksByProject());
        setEmployeeQueryOrder();
        setTaskQueryOrder();

        ResultSet employeesResultSet = taskAllocationMethod.executeQuery(TaskAllocationMethod.EMPLOYEE_QUERY);
        ResultSet taskResultSet = taskAllocationMethod.executeQuery(TaskAllocationMethod.TASK_QUERY);

        Graph<Employee,Task> taskAllocationGraph = taskAllocationMethod.buildGraph(employeesResultSet,taskResultSet);

        taskAllocationMethod.allocateTasks(taskAllocationGraph);

        return buildRows(taskAllocationGraph);
    }

    private void setEmployeeQueryOrder(){
        OrderType employeeOrder = AdvancedOptions.getEmployeeOrder();
        switch (employeeOrder)
        {
            case NAME_ALPHABETICAL: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_ALPHABETICAL,
                    TaskAllocationMethod.EMPLOYEE_QUERY);break;
            case NAME_REVERSE_ALPHABETICAL: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_ALPHABETICAL,
                    TaskAllocationMethod.EMPLOYEE_QUERY);break;
            case COST_ASCENDING: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_COST_LOW_TO_HIGH,
                    TaskAllocationMethod.EMPLOYEE_QUERY);break;
            case COST_DESCENDING: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_COST_HIGH_TO_LOW,
                    TaskAllocationMethod.EMPLOYEE_QUERY);break;
            default: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_ALPHABETICAL,
                    TaskAllocationMethod.EMPLOYEE_QUERY);break;
        }
    }

    private void setTaskQueryOrder(){
        OrderType tasksOrder = AdvancedOptions.getTasksOrder();
        switch (tasksOrder)
        {
            case NONE:break;
            case NAME_ALPHABETICAL: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_ALPHABETICAL,
                    TaskAllocationMethod.TASK_QUERY);break;
            case NAME_REVERSE_ALPHABETICAL: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_REVERSE_ALPHABETICAL,
                    TaskAllocationMethod.TASK_QUERY);break;
            default:break;
        }
    }

    private ArrayList<Object[]> buildRows(Graph<Employee,Task> taskAllocationGraph){
        ArrayList<Object[]> tableRows = new ArrayList<>();
        Set<Map.Entry<Employee,Task>> entrySet = taskAllocationGraph.getEmployeeToTaskMapping().entrySet();
        for (Map.Entry<Employee, Task> employeeTaskEntry : entrySet) {
            Employee e = employeeTaskEntry.getKey();
            Task t = employeeTaskEntry.getValue();
            //The last column will always be initially false, as the user has not
            //been assigned any of the tasks within the table
            Object [] rowData = {e.getId(),e.getName(),t.getTaskName(),t.getId(),false};
            tableRows.add(rowData);
        }
        return tableRows;
    }
}
