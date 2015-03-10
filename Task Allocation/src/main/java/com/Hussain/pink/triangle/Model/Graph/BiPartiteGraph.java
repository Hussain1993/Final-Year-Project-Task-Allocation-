package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import com.google.common.collect.LinkedListMultimap;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

/**
 * Graph to hold the employee and task objects
 * There are three main data structures within this class.
 *
 * LinkedListMultimap - This is will be the Bipartite graph, represented as
 * an adjacency list for example: E1 -> T1, T1 -> E1
 * This allows for quickly accessing the adjacent nodes of a given node
 *
 * EmployeeIndexMap - This will be a mapping from the employee name to their
 * corresponding employee object which will hold the rest of their attributes
 *
 * TaskIndexMap - This will be a mapping from the task name to the task
 * object which will hold the rest of the tasks attributes
 *
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

    /**
     * Add a new employee to the index map
     * @param employee The employee to add to the map
     */
    public void addEmployeeToIndexMap(Employee employee){
        String employeeName = employee.getName();
        employeeIndexMap.put(employeeName,employee);
    }

    /**
     * Add a new task to the index map
     * @param task The task to add to the index map
     */
    public void addTaskToIndexMap(Task task){
        String taskName = task.getTaskName();
        taskIndexMap.put(taskName,task);
    }

    /**
     * Get all the employee nodes within the index map,
     * Note this method will only return a set containing the employees name
     * @return A set containing all the names of the employees within
     * this bipartite graph object
     */
    public Set<String> getEmployeeNodes(){
        return employeeIndexMap.keySet();
    }

    /**
     * Get all the task nodes within the index map,
     * Note this method will only return a set containing the tasks name
     * @return A set containing all the names of the tasks within
     * this bipartite graph object
     */
    public Set<String> getTaskNodes(){
        return taskIndexMap.keySet();
    }

    /**
     * This method is purely used from the equals method
     * @return The bipartite graph object
     */
    public LinkedListMultimap<String, String> getBiPartiteGraph() {
        return biPartiteGraph;
    }

    /**
     * Get the employee from the index map, using their name
     * @param employeeName The name to search for, if the name is not in the map,
     *                     then this method will return null
     * @return The corresponding Employee object if the map contains the string key,
     * null otherwise
     */
    public Employee getEmployeeByName(String employeeName){
        return employeeIndexMap.get(employeeName);
    }

    /**
     * Get the task from the index map, using the task name
     * @param taskName The name to search for, if the name is not in the map,
     *                     then this method will return null
     * @return The corresponding Task object if the map contains the string key,
     * null otherwise
     */
    public Task getTaskByName(String taskName){
        return taskIndexMap.get(taskName);
    }

    /**
     * Adds a new edge to the bipartite graph
     * @param employee The employee object to add
     * @param task The task object to add
     */
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

    /**
     * Gets all the adjacent nodes of a given node
     * @param node The node to get the adjacent nodes of
     * @return A list of all the adjacent nodes
     */
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
