package com.Hussain.pink.triangle.Graph;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by Hussain on 14/11/2014.
 */
public class Graph<E,T> {
    private Map<E,T> employeeToTaskMapping;
    private LinkedHashSet<E> employeeNodes;
    private LinkedHashSet<T> taskNodes;

    public Graph() {
        employeeToTaskMapping = new HashMap<>();
        employeeNodes = new LinkedHashSet<>();
        taskNodes = new LinkedHashSet<>();
    }

    public Map<E,T> getEmployeeToTaskMapping(){
        return this.employeeToTaskMapping;
    }

    public boolean isEmpty(){
        return this.employeeNodes.isEmpty() && this.taskNodes.isEmpty() && employeeToTaskMapping.isEmpty();
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
