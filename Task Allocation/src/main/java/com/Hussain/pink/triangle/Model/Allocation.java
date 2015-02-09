package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Allocation.BiPartiteMatching;
import com.Hussain.pink.triangle.Allocation.GreedyTaskAllocation;
import com.Hussain.pink.triangle.Allocation.TaskAllocationMethod;
import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class takes the options that the user
 * has specified and computes the allocation of the tasks
 * taking into account the advanced options etc...
 *
 * Created by Hussain on 03/12/2014.
 */
public class Allocation {
    private static final Logger LOG = LoggerFactory.getLogger(Allocation.class);

    private TaskAllocationMethod taskAllocationMethod;
    private ResultSet employeesResultSet;
    private ResultSet tasksResultSet;

    /**
     * Make a new allocation depending on which algorithm that should be used
     * @param algorithm Either greedy or maximum
     */
    public Allocation(int algorithm){
        switch(algorithm)
        {
            case 0: taskAllocationMethod = new GreedyTaskAllocation();break;
            case 1: taskAllocationMethod = new BiPartiteMatching();break;
            default: taskAllocationMethod = new GreedyTaskAllocation(); break; //This is the default method to use
        }
    }

    /**
     * Allocate the tasks to the employees checking the options that the user
     * has chosen
     * @return An ArrayList of rows for the table
     */
    public ArrayList<Object[]> allocateEmployeesAndTasks(){
        //Should the query check if employees have been assigned to tasks?
        taskAllocationMethod.setEmployeeQuery(AdvancedOptions.checkIfEmployeesAreAssignedToTasks());

        //How should we group the tasks by?
        taskAllocationMethod.setTaskGroupOrder(AdvancedOptions.groupTasksByProject());

        //Set these properties within the specified task allocation method
        setEmployeeQueryOrder();
        setTaskQueryOrder();

        Thread employeeThread = new Thread(){
            public void run(){
                employeesResultSet = Allocation.this.taskAllocationMethod.executeQuery(TaskAllocationMethod.EMPLOYEE_QUERY);
            }
        };


        final Thread taskThread = new Thread(){
          public void run(){
              tasksResultSet = Allocation.this.taskAllocationMethod.executeQuery(TaskAllocationMethod.TASK_QUERY);
          }
        };

        employeeThread.start();
        taskThread.start();

        try{
            employeeThread.join();
            taskThread.join();
        }
        catch (InterruptedException e) {
            LOG.error("There was an error with the query threads",e);
        }

        if(employeesResultSet != null && tasksResultSet != null)
        {
            Graph<Node<Employee>,Node<Task>> taskAllocationGraph = taskAllocationMethod.buildGraph(employeesResultSet,tasksResultSet);

            taskAllocationMethod.allocateTasks(taskAllocationGraph);

            return buildRows(taskAllocationGraph);//Build and return the rows
        }
        return new ArrayList<>();//Return an empty array list at the end
    }

    /**
     * Sets the property of employee order for the query
     */
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

    /**
     * Sets the property of the task query order
     */
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

    /**
     * Takes the allocation graph with the edges added and creates
     * a arraylist of rows that can be used for the TaskAllocation table model
     * to be displayed to the user
     * @param taskAllocationGraph The task allocation graph
     * @return A list of rows to be displayed
     */
    private ArrayList<Object[]> buildRows(Graph<Node<Employee>,Node<Task>> taskAllocationGraph) {
        ArrayList<Object[]> tableRows = new ArrayList<>();
        if(taskAllocationGraph.hasEdges())
        {
            List<Map.Entry<Node<Employee>,Node<Task>>> entries = taskAllocationGraph.getEmployeeToTaskMapping().entries();
            for (Map.Entry<Node<Employee>,Node<Task>> employeeTaskEntry : entries)
            {
                Node<Employee> employeeNode = employeeTaskEntry.getKey();
                Node<Task> taskNode = employeeTaskEntry.getValue();
                Employee e = employeeNode.getObject();
                Task t = taskNode.getObject();
                //The last column will always be initially false, as the user has not
                // been assigned any of the tasks within the table
                Object [] rowData = {e.getId(),e.getName(),t.getTaskName(),t.getId(),false};
                tableRows.add(rowData);
            }
        }
        else
        {
            LOG.info("There were no allocations within the graph, returning an empty table");
        }
        return tableRows;
    }
}
