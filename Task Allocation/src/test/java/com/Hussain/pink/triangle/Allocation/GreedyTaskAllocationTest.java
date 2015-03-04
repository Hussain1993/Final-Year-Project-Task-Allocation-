package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;

import static junit.framework.TestCase.assertTrue;

public class GreedyTaskAllocationTest {

    @Test
    public void testGreedy() {
        Employee e1 = new Employee(1, "E1", null, 0);
        Employee e2 = new Employee(2, "E2", null, 0);

        Task t1 = new Task(1,"T1",null,1L,1L,false,null);
        Task t2 = new Task(2,"T2",null,1L,1L,false,null);

        BiPartiteGraph biPartiteGraph = new BiPartiteGraph();

        biPartiteGraph.addEmployeeToIndexMap(e1);
        biPartiteGraph.addEmployeeToIndexMap(e2);
        biPartiteGraph.addTaskToIndexMap(t1);
        biPartiteGraph.addTaskToIndexMap(t2);

        biPartiteGraph.addEdge(e1,t1);
        biPartiteGraph.addEdge(e2,t1);
        biPartiteGraph.addEdge(e2,t2);

        HashMap<String,String> expectedMatching = new HashMap<>();
        expectedMatching.put("E1","T1");
        expectedMatching.put("E2","T2");

        TaskAllocationMethod greedyTaskAllocation = new GreedyTaskAllocation();
        AdvancedOptions.setUseHeuristic(false);//Just to make sure
        Matching<String> matching = greedyTaskAllocation.allocateTasks(biPartiteGraph);
        assertTrue(expectedMatching.equals(matching.getMatching()));
    }

    @Test
    public void testGreedyHeuristic() {
        final Skill java = new Skill("Java",1);
        final Skill java4 = new Skill("Java",4);
        final Skill uml = new Skill("UML",1);
        final Skill xml = new Skill("XML",1);

        //Skill set for the employees
        LinkedHashSet<Skill> e1SkillSet = new LinkedHashSet<Skill>(){{
            add(java4); add(uml); add(xml);
        }};

        LinkedHashSet<Skill> e2SkillSet = new LinkedHashSet<Skill>(){{
           add(java);
        }};

        //Skill set for the tasks
        LinkedHashSet<Skill> t1SkillSet = new LinkedHashSet<Skill>(){{
           add(java);
        }};

        LinkedHashSet<Skill> t2SkillSet = new LinkedHashSet<Skill>(){{
           add(java4); add(uml);
        }};

        Employee e1 = new Employee(1, "E1", e1SkillSet, 0);
        Employee e2 = new Employee(2, "E2", e2SkillSet, 0);

        Task t1 = new Task(1,"T1",null,1L,1L,false,t1SkillSet);
        Task t2 = new Task(2,"T2",null,1L,1L,false,t2SkillSet);

        BiPartiteGraph biPartiteGraph = new BiPartiteGraph();

        biPartiteGraph.addEmployeeToIndexMap(e1);
        biPartiteGraph.addEmployeeToIndexMap(e2);

        biPartiteGraph.addTaskToIndexMap(t1);
        biPartiteGraph.addTaskToIndexMap(t2);

        biPartiteGraph.addEdge(e1,t1);
        biPartiteGraph.addEdge(e1,t2);
        biPartiteGraph.addEdge(e2,t1);

        AdvancedOptions.setUseHeuristic(true);

        TaskAllocationMethod greedy = new GreedyTaskAllocation();

        HashMap<String,String> expectedMatching = new HashMap<>();
        expectedMatching.put("E2","T1");
        expectedMatching.put("E1","T2");

        Matching<String> matching = greedy.allocateTasks(biPartiteGraph);
        assertTrue(expectedMatching.equals(matching.getMatching()));

    }
}