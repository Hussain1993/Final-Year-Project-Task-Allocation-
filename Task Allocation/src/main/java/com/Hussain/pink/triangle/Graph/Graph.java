package com.Hussain.pink.triangle.Graph;

import java.util.*;

/**
 * Created by Hussain on 14/11/2014.
 */
public class Graph<E,T> {
    private Map<E,T> employeeToTaskMapping;
    private Set<E> employeeNodes;
    private Set<T> taskNodes;

    public Graph() {
        employeeToTaskMapping = new HashMap<>();
        employeeNodes = new HashSet<>();
        taskNodes = new HashSet<>();
    }

    public Map<E,T> getEmployeeToTaskMapping(){
        return this.employeeToTaskMapping;
    }

    public boolean isEmpty(){
        return this.employeeNodes.isEmpty() && this.taskNodes.isEmpty();
    }

    public void addEmployeeNode(E employee){
        employeeNodes.add(employee);
    }

    public void addTaskNode(T task){
        taskNodes.add(task);
    }

    public Set<E> getEmployeeNodes(){
        return this.employeeNodes;
    }

    public Set<T> getTaskNodes(){
        return this.taskNodes;
    }

    public void addEdge(E source, T destination){
        //Check if the mapping already exists
        T taskMapped = employeeToTaskMapping.get(source);
        if(taskMapped == null)
        {
            //There is no mapping, now make sure that the
            //employee and task nodes exists in the sets
            if(employeeNodes.contains(source) && taskNodes.contains(destination))
            {
                employeeToTaskMapping.put(source,destination);
            }
            else
            {
                employeeNodes.add(source);
                taskNodes.add(destination);
                employeeToTaskMapping.put(source, destination);
            }
        }
    }

    public boolean hasRelationship(E source, T destination){
        if(source == null && destination == null)
        {
            return true;
        }
        if(source != null && destination == null)
        {
            return false;
        }
        if(source == null && destination != null)
        {
            return false;
        }
        if(employeeToTaskMapping.containsKey(source))
        {
            T mappedTask = employeeToTaskMapping.get(source);
            if(mappedTask.equals(destination))
            {
                return true;
            }
        }
        return false;
    }

    public T getMappedTask(E employee){
        return employeeToTaskMapping.get(employee);
    }
}
