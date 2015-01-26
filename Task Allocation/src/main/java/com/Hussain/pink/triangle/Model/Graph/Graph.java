package com.Hussain.pink.triangle.Model.Graph;

import com.google.common.collect.HashMultimap;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * This class will be used to represent the
 * allocation graph within the application, internally
 * I am using a multimap so that one employee can be mapped to more than
 * one task which is the case when the user has chosen to do maximum
 * matching
 * Created by Hussain on 14/11/2014.
 */
public class Graph<E,T> {
    private HashMultimap<E,T> employeeMatching;
    private LinkedHashSet<E> employeeNodes;
    private LinkedHashSet<T> taskNodes;
    private int numberOfEdges = 0;

    /**
     * Create an empty graph
     */
    public Graph() {
        employeeMatching = HashMultimap.create();
        employeeNodes = new LinkedHashSet<>();
        taskNodes = new LinkedHashSet<>();
    }

    /**
     * Create a graph where there are employees and tasks
     * but there are no edges between them
     * @param employeeNodes Nodes to be added
     * @param taskNodes Nodes to be added
     */
    public Graph(LinkedHashSet<E> employeeNodes, LinkedHashSet<T> taskNodes){
        employeeMatching = HashMultimap.create();
        this.employeeNodes  = employeeNodes;
        this.taskNodes = taskNodes;
    }

    /**
     * Return the allocation graph
     * @return The graph representing the allocations
     */
    public HashMultimap<E,T> getEmployeeToTaskMapping(){
        return this.employeeMatching;
    }

    /**
     * Check if the graph is empty
     * @return True if there are no employee nodes or task nodes and there
     * are no matching between the nodes, false otherwise
     */
    public boolean isEmpty(){
        return this.employeeNodes.isEmpty() && this.taskNodes.isEmpty() && employeeMatching.isEmpty();
    }

    /**
     * Adds a new employee node to the graph
     * @param employee To be added to the graph
     */
    public void addEmployeeNode(E employee){
        employeeNodes.add(employee);
    }

    /**
     * Adds a new task node to the graph
     * @param task To be added to the graph
     */
    public void addTaskNode(T task){
        taskNodes.add(task);
    }

    /**
     * Get all the employee nodes within the graph
     * @return A set containing all the employees within the graph
     */
    public LinkedHashSet<E> getEmployeeNodes(){
        return this.employeeNodes;
    }

    /**
     * Get all the tasks nodes within the graph
     * @return A set containing all the tasks within the graph
     */
    public LinkedHashSet<T> getTaskNodes(){
        return this.taskNodes;
    }

    /**
     * Add an edge in the graph between a employee source and
     * a task destination
     * @param source The employee source
     * @param destination The task that the employee has been matched up
     *                    with
     */
    public void addEdge(E source, T destination){
        //Check if the mapping already exists
        boolean doesMappingExist = employeeMatching.containsEntry(source, destination);
        if(!doesMappingExist)
        {
            //There is no mapping, now make sure that the
            //employee and task nodes exists in the sets
            if(employeeNodes.contains(source) && taskNodes.contains(destination))
            {
                employeeMatching.put(source,destination);
            }
            else
            {
                employeeNodes.add(source);
                taskNodes.add(destination);
                employeeMatching.put(source, destination);
            }
            numberOfEdges++;
        }
    }

    /**
     * Checks to see if there is an edge between the employee and
     * task
     * @param source The employee
     * @param destination The task
     * @return True if there is edge between the employee and task within
     * the graph false otherwise
     */
    public boolean hasRelationship(E source, T destination) {
        return source == null && destination == null || !(source != null && destination == null)
                && !(source == null && destination != null)
                && employeeMatching.containsKey(source) && employeeMatching.containsEntry(source, destination);
    }

    /**
     * Get set of all the mapped tasks for an employee
     * @param employee The employee to get all the mapped tasks for
     * @return A set of mapped tasks for the employee
     */
    public Set<T> getMappedTask(E employee){
        return employeeMatching.get(employee);
    }

    /**
     * Checks to see if there are edges within the graph
     * @return True if there is one or more edges within the graph,
     * false otherwise
     */
    public boolean hasEdges(){
        return numberOfEdges > 0;
    }
}
