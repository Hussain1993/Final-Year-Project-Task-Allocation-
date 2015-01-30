package com.Hussain.pink.triangle.Model.Graph;

import com.google.common.collect.LinkedListMultimap;

import java.util.ArrayList;
import java.util.List;

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
    private LinkedListMultimap<E,T> employeeMatching;
    private ArrayList<E> employeeNodes;
    private ArrayList<T> taskNodes;
    private int numberOfEdges = 0;

    /**
     * Create an empty graph
     */
    public Graph() {
        employeeMatching = LinkedListMultimap.create();
        employeeNodes = new ArrayList<>();
        taskNodes = new ArrayList<>();
    }

    /**
     * Create a graph where there are employees and tasks
     * but there are no edges between them
     * @param employeeNodes Nodes to be added
     * @param taskNodes Nodes to be added
     */
    public Graph(ArrayList<E> employeeNodes, ArrayList<T> taskNodes){
        employeeMatching = LinkedListMultimap.create();
        this.employeeNodes  = employeeNodes;
        this.taskNodes = taskNodes;
    }

    /**
     * Return the allocation graph
     * @return The graph representing the allocations
     */
    public LinkedListMultimap<E,T> getEmployeeToTaskMapping(){
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
    public ArrayList<E> getEmployeeNodes(){
        return this.employeeNodes;
    }

    /**
     * Get all the tasks nodes within the graph
     * @return A set containing all the tasks within the graph
     */
    public ArrayList<T> getTaskNodes(){
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
    public List<T> getMappedTask(E employee){
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

    public void clear(){
        this.employeeMatching.clear();
        this.employeeNodes.clear();
        this.taskNodes.clear();
        numberOfEdges = 0;
    }

    public boolean equals(Object other){
        if(other == null)
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }
        if(!(other instanceof Graph))
        {
            return false;
        }
        Graph otherGraph = (Graph) other;
        return this.getEmployeeNodes().containsAll(otherGraph.getEmployeeNodes())
                && this.getTaskNodes().containsAll(otherGraph.getTaskNodes())
                && this.getEmployeeToTaskMapping().equals(otherGraph.getEmployeeToTaskMapping());
    }
}
