package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BiPartiteGraphTest {

    @Test
    public void testAddEmployeeToIndexMap() {
        BiPartiteGraph graph = new BiPartiteGraph();
        Employee e = new Employee(1,"e1",null,0);
        graph.addEmployeeToIndexMap(e);
        assertEquals(e,graph.getEmployeeByName("e1"));
    }

    @Test
    public void testAddTaskToIndexMap() {
        BiPartiteGraph graph = new BiPartiteGraph();
        Task t = new Task(100,"T1",null,1L,1L,false,null);
        graph.addTaskToIndexMap(t);
        assertEquals(t,graph.getTaskByName("T1"));
    }

    @Test
    public void testGetAdjacentNodes() {
        List<String> e1Expected = new ArrayList<String>(){{
           add("T1"); add("T2");
        }};

        List<String> e2Expected = new ArrayList<String>(){{
           add("T1");
        }};

        List<String> t1Expected = new ArrayList<String>(){{
           add("E1"); add("E2");
        }};

        List<String> t2Expected = new ArrayList<String>(){{
            add("E1");
        }};

        Employee e1 = new Employee(1,"E1",null,0);
        Employee e2 = new Employee(2,"E2",null,0);

        Task t1 = new Task(1,"T1",null,1L,1L,false,null);
        Task t2 = new Task(2,"T2",null,1L,1L,false,null);

        BiPartiteGraph graph = new BiPartiteGraph();

        graph.addEmployeeToIndexMap(e1);
        graph.addEmployeeToIndexMap(e2);

        graph.addTaskToIndexMap(t1);
        graph.addTaskToIndexMap(t2);

        graph.addEdge(e1,t1);
        graph.addEdge(e1,t2);
        graph.addEdge(e2,t1);

        assertEquals(e1Expected,graph.getAdjacentNodes("E1"));
        assertEquals(e2Expected,graph.getAdjacentNodes("E2"));
        assertEquals(t1Expected, graph.getAdjacentNodes("T1"));
        assertEquals(t2Expected,graph.getAdjacentNodes("T2"));
    }

    @Test
    public void testGetEmployeeNodes() {
        LinkedHashSet<String> expectedOrderOfEmployees = new LinkedHashSet<String>(){{
            add("E1"); add("E2");
        }};
        Employee e1 = new Employee(1,"E1",null,0);
        Employee e2 = new Employee(2,"E2",null,0);

        BiPartiteGraph graph = new BiPartiteGraph();
        graph.addEmployeeToIndexMap(e1);
        graph.addEmployeeToIndexMap(e2);

        assertEquals(expectedOrderOfEmployees,graph.getEmployeeNodes());//Make sure the order is correct
    }

    @Test
    public void testGetTaskNodes() {
        LinkedHashSet<String> expectedOrderOfTasks = new LinkedHashSet<String>(){{
           add("T1"); add("T2");
        }};

        Task t1 = new Task(1,"T1",null,1L,1L,false,null);
        Task t2 = new Task(2,"T2",null,1L,1L,false,null);

        BiPartiteGraph graph = new BiPartiteGraph();
        graph.addTaskToIndexMap(t1);
        graph.addTaskToIndexMap(t2);

        assertEquals(expectedOrderOfTasks,graph.getTaskNodes());//Make sure the order is correct
    }
}