package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Allocation.BiPartiteMatching;
import com.Hussain.pink.triangle.Allocation.GreedyTaskAllocation;
import com.Hussain.pink.triangle.Allocation.Matching;
import com.Hussain.pink.triangle.Allocation.TaskAllocationMethod;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.GroupTask;
import com.Hussain.pink.triangle.Organisation.Project;
import com.Hussain.pink.triangle.Organisation.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

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

        final CyclicBarrier gate = new CyclicBarrier(3);


        Thread employeeThread = new Thread(){
            public void run(){
                try {
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error when trying to make the thread wait",e);
                }
                employeesResultSet = Allocation.this.taskAllocationMethod.executeQuery(TaskAllocationMethod.EMPLOYEE_QUERY);
            }
        };


        final Thread taskThread = new Thread(){
          public void run(){
              try {
                  gate.await();
              }
              catch (InterruptedException | BrokenBarrierException e) {
                  LOG.error("There was an error when trying to make the thread wait",e);
              }
              tasksResultSet = Allocation.this.taskAllocationMethod.executeQuery(TaskAllocationMethod.TASK_QUERY);
          }
        };

        employeeThread.start();
        taskThread.start();

        try{
            gate.await();
            //Wait for the two threads to finish
            employeeThread.join();
            taskThread.join();
        }
        catch (InterruptedException | BrokenBarrierException e) {
            LOG.error("There was an error with the query threads",e);
        }

        if(employeesResultSet != null && tasksResultSet != null)
        {
            BiPartiteGraph biPartiteGraph = taskAllocationMethod.buildGraph(employeesResultSet,tasksResultSet);

            Matching<String> matching = taskAllocationMethod.allocateTasks(biPartiteGraph);

            if(AdvancedOptions.groupTasksByProject())
            {
                for(Project p : GroupTask.getUnassignedProjects())
                {
                    int numberOfOutstandingTasks = GroupTask.getNumberOfMappedTasksForProject(p);
                    LOG.info("The project {} has {} outstanding task(s)",p.getProjectName(),numberOfOutstandingTasks);
                }
            }
            return buildRows(matching,biPartiteGraph);//Build and return the rows
        }
        return new ArrayList<>();//Return an empty array list if there ever is an error
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

    private ArrayList<Object[]> buildRows(Matching<String> matching, BiPartiteGraph biPartiteGraph){
        ArrayList<Object[]> tableRows = new ArrayList<>();
        if(matching.hasMatch())
        {
            HashMap<String,String> employeeToTaskMapping = matching.getMatching();
            for (String employeeName : employeeToTaskMapping.keySet())
            {
                Employee employee = biPartiteGraph.getEmployeeByName(employeeName);
                Task task = biPartiteGraph.getTaskByName(employeeToTaskMapping.get(employeeName));
                Object [] rowData = {employee.getId(),employee.getName(),task.getTaskName(),task.getId(),true};
                tableRows.add(rowData);
            }
        }
        else
        {
            LOG.info("There were no matches within the graph, returning an empty table");
        }
        return tableRows;
    }
}
