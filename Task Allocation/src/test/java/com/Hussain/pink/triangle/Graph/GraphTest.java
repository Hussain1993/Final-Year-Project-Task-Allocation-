package com.Hussain.pink.triangle.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
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
}