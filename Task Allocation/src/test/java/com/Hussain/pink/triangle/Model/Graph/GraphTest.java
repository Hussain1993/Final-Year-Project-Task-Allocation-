package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    @Test
    public void testIsEmpty() {
        Graph<Node<Employee>,Node<Task>> t = new Graph<>();
        assertTrue(t.isEmpty());
    }

    @Test
    public void testGetEmployeeNodes() {
        Node<Employee> employeeNode = new Node<>(new Employee(1,"Hussain",null,0), NodeType.EMPLOYEE);
        Set<Node<Employee>> set = new HashSet<>();
        set.add(employeeNode);
        Graph<Node<Employee>, Node<Task>> graph = new Graph<>();
        graph.addEmployeeNode(employeeNode);
        assertEquals(set,graph.getEmployeeNodes());
    }

    @Test
    public void testGetTaskNodes() {
        Node<Task> taskNode = new Node<>(new Task(1,"",1,1,1,false,null), NodeType.TASK);
        Set<Node<Task>> t = new HashSet<>();
        t.add(taskNode);
        Graph<Node<Employee>, Node<Task>> graph = new Graph<>();
        graph.addTaskNode(taskNode);
        assertEquals(t,graph.getTaskNodes());
    }

    @Test
    public void testAddEdge() {
        Node<Employee> employeeNode = new Node<>(new Employee(1,"",null,0), NodeType.EMPLOYEE);
        Node<Task> taskNode = new Node<>(new Task(1,"",1,1,1,false,null),NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> graph = new Graph<>();

        graph.addEmployeeNode(employeeNode);
        graph.addTaskNode(taskNode);

        graph.addEdge(employeeNode,taskNode);

        assertTrue(graph.hasRelationship(employeeNode,taskNode));

    }

    @Test
    public void testGetMappedTask(){
        Node<Employee> employeeNode = new Node<>(new Employee(1,"Test Employee",null,0),NodeType.EMPLOYEE);
        Node<Task> taskNode = new Node<>(new Task(1,"Test Task",1,1L,1L,false,null), NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(employeeNode);
        testGraph.addTaskNode(taskNode);

        testGraph.addEdge(employeeNode,taskNode);

        Set<Node<Task>> mappedTasks = new LinkedHashSet<>();
        mappedTasks.add(taskNode);

        assertTrue(mappedTasks.containsAll(testGraph.getMappedTask(employeeNode)));
    }

    @Test
    public void testMultiMapGraph() {
        Node<Employee> employeeNode = new Node<>(new Employee(1,"Test Employee",null,0), NodeType.EMPLOYEE);
        Node<Task> t1 = new Node<>(new Task(1,"Task 1",1,1L,1L,false,null), NodeType.TASK);
        Node<Task> t2 = new Node<>(new Task(2,"Task 2",2,1L,1L,false,null), NodeType.TASK);

        Graph<Node<Employee>,Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(employeeNode);
        testGraph.addTaskNode(t1);
        testGraph.addTaskNode(t2);

        testGraph.addEdge(employeeNode,t1);
        testGraph.addEdge(employeeNode,t2);

        assertTrue(testGraph.hasRelationship(employeeNode,t1) && testGraph.hasRelationship(employeeNode,t2));
    }

    @Test
    public void testAreThereEdgesTrue() {
        ArrayList<String> set1 = new ArrayList<String>(){{add("Test");}};
        ArrayList<String> set2 = new ArrayList<String>(){{add("Test");}};

        Graph<String,String> testGraph = new Graph<>(set1,set2);

        testGraph.addEdge("Test","Test");

        assertTrue(testGraph.hasEdges());
    }

    @Test
    public void testAreThereEdgesFalse() {
        ArrayList<String> set1 = new ArrayList<String>(){{add("Test");}};
        ArrayList<String> set2 = new ArrayList<String>(){{add("Test");}};

        Graph<String,String> testGraph = new Graph<>(set1,set2);

        assertFalse(testGraph.hasEdges());

    }
}