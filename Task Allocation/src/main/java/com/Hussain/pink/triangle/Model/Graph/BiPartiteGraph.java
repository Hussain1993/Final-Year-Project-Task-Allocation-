package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import com.google.common.collect.LinkedListMultimap;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Hussain on 02/03/2015.
 */
public class BiPartiteGraph {
    private LinkedListMultimap<String,String> biPartiteGraph;

    private HashMap<String,Employee> employeeIndexMap;
    private HashMap<String,Task> taskIndexMap;

    public BiPartiteGraph(){
        biPartiteGraph = LinkedListMultimap.create();
        employeeIndexMap = new HashMap<>();
        taskIndexMap = new HashMap<>();
    }

    public void addEmployeeToIndexMap(Employee employee){
        String employeeName = employee.getName();
        employeeIndexMap.put(employeeName,employee);
    }

    public void addTaskToIndexMap(Task task){
        String taskName = task.getTaskName();
        taskIndexMap.put(taskName,task);
    }

    public Employee getEmployeeByName(String employeeName){
        return employeeIndexMap.get(employeeName);
    }

    public Task getTaskByName(String taskName){
        return taskIndexMap.get(taskName);
    }

    public void addEdge(Employee employee, Task task){
        String employeeName = employee.getName();
        String taskName = task.getTaskName();
        //There is already both these entries within the map
        if(biPartiteGraph.containsEntry(employeeName,taskName) && biPartiteGraph.containsEntry(taskName,employeeName))
        {
            return;
        }
        //Make the employee map to the task and the task to the employee
        biPartiteGraph.put(employeeName,taskName);
        biPartiteGraph.put(taskName,employeeName);
    }

    public List<String> getAdjacentNodes(String node){
        return biPartiteGraph.get(node);//The node is either a employee or task node
    }
}
