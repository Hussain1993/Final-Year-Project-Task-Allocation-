package com.Hussain.pink.triangle.Allocation;

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
        //This has to be a better test
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

        TaskAllocationMethod greedyMethod = new GreedyTaskAllocation();
        greedyMethod.allocateTasks(testGraph);//Allocate the employees and tasks within the graph

        assertTrue(testGraph.getEmployeeNodes().size() == 1);//Assert there is only one employee in the graph
        assertTrue(testGraph.getTaskNodes().size() == 1);//Assert there is only one task in the graph
        assertTrue(testGraph.getMappedTask(e1).size() == 1);//Assert there is only one mapped task for the employee
        assertTrue(testGraph.hasRelationship(e1,t1));//Assert there is a mapping between the employee and task
    }

    @Test
    public void testName() {
        final Skill java = new Skill("Java", 1);
        final Skill java4 = new Skill("Java", 4);
        final Skill uml = new Skill("UML", 1);
        final Skill xml = new Skill("XML",2);
        final Skill iOS = new Skill("iOS",2);
        final Skill cPlusPlus = new Skill("C++",1);
        final Skill windowsPhone = new Skill("Windows Phone", 3);

        LinkedHashSet<Skill> taskSkills = new LinkedHashSet<Skill>(){{
            add(java); add(uml);
        }};


        LinkedHashSet<Skill> e1SkillSet = new LinkedHashSet<Skill>(){{
            add(java4); add(uml);
        }};

        LinkedHashSet<Skill> e2SkillSet = new LinkedHashSet<Skill>(){{
            add(java); add(xml); add(uml);
        }};

        LinkedHashSet<Skill> e3SkillSet = new LinkedHashSet<Skill>(){{
           add(java); add(uml); add(iOS); add(cPlusPlus); add(windowsPhone);
        }};

        Node<Employee> e1 = new Node<>(new Employee(1,"E1",e1SkillSet,0),NodeType.EMPLOYEE);
        final Node<Employee> e2 = new Node<>(new Employee(2,"E2", e2SkillSet,0),NodeType.EMPLOYEE);
        Node<Employee> e3 = new Node<>(new Employee(3,"E3", e3SkillSet, 0),NodeType.EMPLOYEE);

        List<Node<Employee>> expectedList = new ArrayList<Node<Employee>>(){{
            add(e2);
        }};

        Node<Task> t1 = new Node<>(new Task(1,"T1",10,1L,1L,false,taskSkills),NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);
        testGraph.addEmployeeNode(e3);

        testGraph.addTaskNode(t1);

        testGraph.addEdge(e1,t1);
        testGraph.addEdge(e2,t1);
        testGraph.addEdge(e3,t1);

        assertEquals(3, testGraph.getEmployeeToTaskMapping().size());//Test there are 3 allocations in the graph

        List<Node<Employee>> employeeNodes = testGraph.getEmployeeNodes();
        List<Node<Task>> taskNodes = testGraph.getTaskNodes();

        GreedyTaskAllocation greedyTaskAllocation = new GreedyTaskAllocation();
        greedyTaskAllocation.greedyHeuristic(employeeNodes,taskNodes,testGraph);

        assertEquals(1, testGraph.getEmployeeToTaskMapping().size());
        assertEquals(expectedList, testGraph.getMappedEmployees(t1));
    }
}