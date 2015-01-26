package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GraphTest {

    @Test
    public void testIsEmpty() {
        Graph<Employee,Task> t = new Graph<>();
        assertTrue(t.isEmpty());
    }

    @Test
    public void testGetEmployeeNodes() {
        Set<Employee> set = new HashSet<>();
        Employee e = new Employee(1,"Hussain",null,0);
        set.add(e);
        Graph<Employee, Task> graph = new Graph<>();
        graph.addEmployeeNode(e);
        assertEquals(set,graph.getEmployeeNodes());
    }

    @Test
    public void testGetTaskNodes() {
        Set<Task> t = new HashSet<>();
        Task task = new Task(1,null,1,1,1,false,null);
        t.add(task);
        Graph<Employee, Task> graph = new Graph<>();
        graph.addTaskNode(task);
        assertEquals(t,graph.getTaskNodes());
    }

    @Test
    public void testAddEdge() {
        Employee e = new Employee(1,null,null,0);
        Task t = new Task(1,null,1,1,1,false,null);

        Graph<Employee, Task> graph = new Graph<>();
        graph.addEmployeeNode(e);
        graph.addTaskNode(t);

        graph.addEdge(e,t);

        assertTrue(graph.hasRelationship(e,t));

    }

    @Test
    public void testGetMappedTask(){
        Employee e = new Employee(1,"Test Employee",null,0);
        Task t = new Task(1,"Test Task",1,1L,1L,false,null);

        Graph<Employee, Task> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e);
        testGraph.addTaskNode(t);

        testGraph.addEdge(e,t);

        Set<Task> mappedTasks = new LinkedHashSet<>();
        mappedTasks.add(t);

        assertTrue(mappedTasks.containsAll(testGraph.getMappedTask(e)));
    }

    @Test
    public void testMultiMapGraph() {
        Employee e = new Employee(1,"Test Employee",null,0);

        Task t1 = new Task(1,"Task 1",1,1L,1L,false,null);
        Task t2 = new Task(2,"Task 2",2,1L,1L,false,null);

        Graph<Employee,Task> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e);
        testGraph.addTaskNode(t1);
        testGraph.addTaskNode(t2);

        testGraph.addEdge(e,t1);
        testGraph.addEdge(e,t2);

        assertTrue(testGraph.hasRelationship(e,t1) && testGraph.hasRelationship(e,t2));
    }

    @Test
    public void testAreThereEdgesTrue() {
        LinkedHashSet<String> set1 = new LinkedHashSet<String>(){{add("Test");}};
        LinkedHashSet<String> set2 = new LinkedHashSet<String>(){{add("Test");}};

        Graph<String,String> testGraph = new Graph<>(set1,set2);

        testGraph.addEdge("Test","Test");

        assertTrue(testGraph.hasEdges());
    }

    @Test
    public void testAreThereEdgesFalse() {
        LinkedHashSet<String> set1 = new LinkedHashSet<String>(){{add("Test");}};
        LinkedHashSet<String> set2 = new LinkedHashSet<String>(){{add("Test");}};

        Graph<String,String> testGraph = new Graph<>(set1,set2);

        assertFalse(testGraph.hasEdges());

    }
}