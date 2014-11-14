package com.Hussain.pink.triangle.Graph;

import java.util.*;

/**
 * Created by Hussain on 14/11/2014.
 */
public class Graph<E,T> {
    private Map<Node<E>,Node<T>> employeeToTaskMapping;
    private Set<Node<E>> employeeNodes;
    private Set<Node<T>> taskNodes;

    public Graph() {
        employeeToTaskMapping = new HashMap<>();
        employeeNodes = new HashSet<>();
        taskNodes = new HashSet<>();
    }

    public Map<Node<E>,Node<T>> getEmployeeToTaskMapping(){
        return this.employeeToTaskMapping;
    }

    public boolean isEmpty(){
        return this.employeeNodes.isEmpty() && this.taskNodes.isEmpty();
    }

    public void addEmployeeNode(E employee){
        employeeNodes.add(new Node<>(employee));
    }

    public void addTaskNode(T task){
        taskNodes.add(new Node<>(task));
    }

    public Set<Node<E>> getEmployeeNodes(){
        return this.employeeNodes;
    }

    public Set<Node<T>> getTaskNodes(){
        return this.taskNodes;
    }

    public void addEdge(Node<E> source, Node<T> destination){
        //Check if the mapping already exists
        Node<T> taskMapped = employeeToTaskMapping.get(source);
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

    public boolean hasRelationship(Node<E> source, Node<T> destination){
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
            Node<T> mappedTask = employeeToTaskMapping.get(source);
            if(mappedTask.equals(destination))
            {
                return false;
            }
        }
        return false;
    }

    public Node<T> getMappedTask(Node<E> employee){
        return employeeToTaskMapping.get(employee);
    }
}
