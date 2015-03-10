package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;

public class HopcroftKarpTest {

    @Test
    public void maximumBiPartiteMatching1(){
        BiPartiteGraph graph = buildGraphForMatching1();
        TaskAllocationMethod bipartiteMatching = new HopcroftKarp();
        Matching<String> matching = bipartiteMatching.allocateTasks(graph);
        HashMap<String, String> expectedMatching = new HashMap<>();
        expectedMatching.put("E1","T2");
        expectedMatching.put("E2","T1");
        assertTrue(expectedMatching.equals(matching.getMatching()));
    }

    @Test
    public void maximumBiPartiteMatching2(){
        BiPartiteGraph graph = buildGraphForMatching2();
        TaskAllocationMethod bipartiteMatching = new HopcroftKarp();
        Matching<String> matching = bipartiteMatching.allocateTasks(graph);
        HashMap<String, String> expectedMap = new HashMap<>();
        expectedMap.put("E1","T2");
        expectedMap.put("E3","T1");
        expectedMap.put("E4","T3");
        expectedMap.put("E5","T4");
        expectedMap.put("E6","T6");
        assertTrue(expectedMap.equals(matching.getMatching()));
    }

    private BiPartiteGraph buildGraphForMatching1(){
        Employee e1 = new Employee(1,"E1",null,0);
        Employee e2 = new Employee(2,"E2",null,0);

        Task t1 = new Task(1,"T1",null,1L,1L,false,null);
        Task t2 = new Task(2,"T2",null,1L,1L,false,null);
        BiPartiteGraph biPartiteGraph = new BiPartiteGraph();
        biPartiteGraph.addEmployeeToIndexMap(e1);
        biPartiteGraph.addEmployeeToIndexMap(e2);
        biPartiteGraph.addTaskToIndexMap(t1);
        biPartiteGraph.addTaskToIndexMap(t2);

        biPartiteGraph.addEdge(e1,t1);
        biPartiteGraph.addEdge(e1,t2);
        biPartiteGraph.addEdge(e2,t1);
        return biPartiteGraph;
    }

    private BiPartiteGraph buildGraphForMatching2(){
        Employee e1 = new Employee(1,"E1",null,0);
        Employee e2 = new Employee(2,"E2",null,0);
        Employee e3 = new Employee(3,"E3",null,0);
        Employee e4 = new Employee(4,"E4",null,0);
        Employee e5 = new Employee(5,"E5",null,0);
        Employee e6 = new Employee(6,"E6",null,0);

        Task t1 = new Task(1,"T1",null,1L,1L,false,null);
        Task t2 = new Task(2,"T2",null,1L,1L,false,null);
        Task t3 = new Task(3,"T3",null,1L,1L,false,null);
        Task t4 = new Task(4,"T4",null,1L,1L,false,null);
        Task t5 = new Task(5,"T5",null,1L,1L,false,null);
        Task t6 = new Task(6,"T6",null,1L,1L,false,null);

        BiPartiteGraph biPartiteGraph = new BiPartiteGraph();

        biPartiteGraph.addEmployeeToIndexMap(e1);
        biPartiteGraph.addEmployeeToIndexMap(e2);
        biPartiteGraph.addEmployeeToIndexMap(e3);
        biPartiteGraph.addEmployeeToIndexMap(e4);
        biPartiteGraph.addEmployeeToIndexMap(e5);
        biPartiteGraph.addEmployeeToIndexMap(e6);

        biPartiteGraph.addTaskToIndexMap(t1);
        biPartiteGraph.addTaskToIndexMap(t2);
        biPartiteGraph.addTaskToIndexMap(t3);
        biPartiteGraph.addTaskToIndexMap(t4);
        biPartiteGraph.addTaskToIndexMap(t5);
        biPartiteGraph.addTaskToIndexMap(t6);

        biPartiteGraph.addEdge(e1,t2);
        biPartiteGraph.addEdge(e1,t3);
        biPartiteGraph.addEdge(e3,t1);
        biPartiteGraph.addEdge(e3,t4);
        biPartiteGraph.addEdge(e4,t3);
        biPartiteGraph.addEdge(e5,t3);
        biPartiteGraph.addEdge(e5,t4);
        biPartiteGraph.addEdge(e6,t6);
        return biPartiteGraph;
    }

}