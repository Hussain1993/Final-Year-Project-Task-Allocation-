package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import com.google.common.collect.LinkedListMultimap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Hussain on 02/03/2015.
 */
public class BiPartiteGraph {
    private LinkedListMultimap<String,String> biPartiteGraph;

    private LinkedHashMap<String,Employee> employeeIndexMap;
    private LinkedHashMap<String,Task> taskIndexMap;

    public BiPartiteGraph(){
        biPartiteGraph = LinkedListMultimap.create();
        employeeIndexMap = new LinkedHashMap<>();
        taskIndexMap = new LinkedHashMap<>();
    }

    public void addEmployeeToIndexMap(Employee employee){
        String employeeName = employee.getName();
        employeeIndexMap.put(employeeName,employee);
    }

    public void addTaskToIndexMap(Task task){
        String taskName = task.getTaskName();
        taskIndexMap.put(taskName,task);
    }

    public Set<String> getEmployeeNodes(){
        return employeeIndexMap.keySet();
    }

    public Set<String> getTaskNodes(){
        return taskIndexMap.keySet();
    }

    public LinkedListMultimap<String, String> getBiPartiteGraph() {
        return biPartiteGraph;
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

    @Override
    public boolean equals(Object other){
        if(other == null)
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }
        if(!(other instanceof BiPartiteGraph))
        {
            return false;
        }
        BiPartiteGraph otherBiPartiteGraph = (BiPartiteGraph) other;
        return this.getBiPartiteGraph().equals(otherBiPartiteGraph.getBiPartiteGraph())
                && this.getEmployeeNodes().containsAll(otherBiPartiteGraph.getEmployeeNodes())
                && this.getTaskNodes().containsAll(otherBiPartiteGraph.getTaskNodes());
    }
}
