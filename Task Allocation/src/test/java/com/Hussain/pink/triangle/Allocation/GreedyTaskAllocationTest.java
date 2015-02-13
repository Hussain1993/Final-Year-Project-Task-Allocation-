package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GreedyTaskAllocationTest {

    @Test
    public void testAllocateTasks() {
        Skill java = new Skill("Java",1);
        Skill uml = new Skill("UML",1);
        LinkedHashSet<Skill> skills = new LinkedHashSet<>();
        skills.add(java);
        skills.add(uml);
        Employee e = new Employee(1,"Test Employee",skills,0);

        Task t = new Task(1,"Test Task", 1,1L,1L,false,skills);

        Graph<Node<Employee>,Node<Task>> testGraph = new Graph<>();
        Node<Employee> e1 = new Node<>(e,NodeType.EMPLOYEE);
        Node<Task> t1 = new Node<>(t,NodeType.TASK);
        testGraph.addEmployeeNode(e1);
        testGraph.addTaskNode(t1);

        AdvancedOptions.setUseHeuristic(false);//Sanity setting

        TaskAllocationMethod greedyMethod = new GreedyTaskAllocation();
        greedyMethod.allocateTasks(testGraph);//Allocate the employees and tasks within the graph

        assertTrue(testGraph.getEmployeeNodes().size() == 1);//Assert there is only one employee in the graph
        assertTrue(testGraph.getTaskNodes().size() == 1);//Assert there is only one task in the graph
        assertTrue(testGraph.getMappedTask(e1).size() == 1);//Assert there is only one mapped task for the employee
        assertTrue(testGraph.hasRelationship(e1,t1));//Assert there is a mapping between the employee and task
    }

    @Test
    public void testAllocateTasks_Heuristic() {
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

        Node<Employee> e1 = new Node<>(new Employee(1,"E1",e1SkillSet,0),NodeType.EMPLOYEE);
        Node<Employee> e2 = new Node<>(new Employee(2,"E2",e2SkillSet,0),NodeType.EMPLOYEE);

        final Node<Task> t1 = new Node<>(new Task(1,"T1",100,1L,1L,false,t1SkillSet),NodeType.TASK);
        final Node<Task> t2 = new Node<>(new Task(2,"T2",100,1L,1L,false,t2SkillSet),NodeType.TASK);

        Graph<Node<Employee>,Node<Task>> testGraph = new Graph<>();
        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);

        testGraph.addTaskNode(t1);
        testGraph.addTaskNode(t2);

        AdvancedOptions.setUseHeuristic(true);//We would like to use the heuristic when finding the allocations

        TaskAllocationMethod greedy = new GreedyTaskAllocation();
        greedy.allocateTasks(testGraph);

        assertEquals(2, testGraph.getNumberOfEdges());//There are 3 edges in the graph originally

        List<Node<Task>> expectedE1 = new ArrayList<Node<Task>>(){{add(t2);}};
        List<Node<Task>> expectedE2 = new ArrayList<Node<Task>>(){{add(t1);}};
        assertEquals(expectedE1,testGraph.getMappedTask(e1));
        assertEquals(expectedE2,testGraph.getMappedTask(e2));
    }

}