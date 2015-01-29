package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

/**
 * Created by Hussain on 27/01/2015.
 */
public class List {
    private static final Node<String> SOURCE = new Node<>("Source",NodeType.SOURCE);
    private static final Node<String> SINK = new Node<>("Sink", NodeType.SINK);

    private HashMap<Node, java.util.List<? extends Node>> list;
    private Graph<Node<Employee>,Node<Task>> allocationGraph;

    public List(Graph<Node<Employee>,Node<Task>> allocationGraph){
        list = new HashMap<>();

        this.allocationGraph = allocationGraph;
        buildList();
    }

    private void buildList(){
        //Make source point to all the employee nodes
        list.put(SOURCE, allocationGraph.getEmployeeNodes());

        //Map the employees and the tasks that they have been matched up with them
        for(Node<Employee> employeeNode : allocationGraph.getEmployeeNodes())
        {
            list.put(employeeNode, allocationGraph.getMappedTask(employeeNode));
        }

        //Go in the reverse direction find the employee that has the task matched with them
        LinkedHashSet taskToEmployeeList = new LinkedHashSet<>();
        for(Node<Task> taskNode : allocationGraph.getTaskNodes())
        {
            for(Node<Employee> employeeNode : allocationGraph.getEmployeeNodes())
            {
                if(allocationGraph.hasRelationship(employeeNode,taskNode))
                {
                    taskToEmployeeList.add(employeeNode);
                }
            }
            if(!taskToEmployeeList.isEmpty())
            {
                taskToEmployeeList.add(SINK);
                ArrayList clonedList = new ArrayList(taskToEmployeeList);
                list.put(taskNode,clonedList);
            }
            taskToEmployeeList.clear();
        }
    }

    public java.util.List<? extends Node> listElements(Node head){
        if(!list.isEmpty() && list.containsKey(head))
        {
            return list.get(head);
        }
        return null;
    }

    public Node getFirstElement(Node head){
        if(!list.isEmpty() && list.containsKey(head))
        {
            java.util.List<? extends Node> elements = listElements(head);
            if(elements.size() > 0)
            {
                return elements.get(0);
            }
        }
        return SINK;
    }

    public void removeFromList(Node head, Node nodeToBeRemoved){
        if(!list.containsKey(head))
        {
            return;
        }
        java.util.List<? extends Node> elements = listElements(head);
        elements.remove(nodeToBeRemoved);
    }

    public Node getOtherEmployeesMappedToSameTask(Node<Task> taskNode, Node<Employee> employeeAssignedNode){
        if(!taskNode.getNodeType().equals(NodeType.TASK))
        {
            return null;
        }
        if(listElements(taskNode) != null && listElements(taskNode).size() == 2)//This means that the task is mapped to one employee only and the other node is the sink
        {
            return getSink(taskNode);
        }
        else if(listElements(taskNode) != null && listElements(taskNode).size() > 2) // This is the case when more than one employee is mapped to the same task
        {
            java.util.List<? extends Node> employeeNodes = listElements(taskNode);
            for(Node<Employee> employeeNode : employeeNodes)
            {
                if(!employeeNode.equals(employeeAssignedNode))
                {
                    return employeeNode;
                }
            }
        }
        return SINK;
    }

    private Node getSink(Node<Task> taskNode){
        java.util.List<? extends Node> elements = listElements(taskNode);
        for(Node node : elements)
        {
            if(node.getNodeType().equals(NodeType.SINK))
            {
                return node;
            }
        }
        return null;
    }
}
