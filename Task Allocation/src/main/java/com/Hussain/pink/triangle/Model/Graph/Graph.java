package com.Hussain.pink.triangle.Model.Graph;

import com.google.common.collect.HashMultimap;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Hussain on 14/11/2014.
 */
public class Graph<E,T> {
    private HashMultimap<E,T> employeeMatching;
    //private Map<E,T> employeeToTaskMapping;
    private LinkedHashSet<E> employeeNodes;
    private LinkedHashSet<T> taskNodes;

    public Graph() {
        employeeMatching = HashMultimap.create();
        employeeNodes = new LinkedHashSet<>();
        taskNodes = new LinkedHashSet<>();
    }

    public HashMultimap<E,T> getEmployeeToTaskMapping(){
        return this.employeeMatching;
    }

    public boolean isEmpty(){
        return this.employeeNodes.isEmpty() && this.taskNodes.isEmpty() && employeeMatching.isEmpty();
    }

    public void addEmployeeNode(E employee){
        employeeNodes.add(employee);
    }

    public void addTaskNode(T task){
        taskNodes.add(task);
    }

    public LinkedHashSet<E> getEmployeeNodes(){
        return this.employeeNodes;
    }

    public LinkedHashSet<T> getTaskNodes(){
        return this.taskNodes;
    }

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
        }
    }

    public boolean hasRelationship(E source, T destination) {
        return source == null && destination == null || !(source != null && destination == null)
                && !(source == null && destination != null)
                && employeeMatching.containsKey(source) && employeeMatching.containsEntry(source, destination);
    }

    public Set<T> getMappedTask(E employee){
        return employeeMatching.get(employee);
    }
}
