package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.LinkedHashSet;

import static org.junit.Assert.assertTrue;

/**
 * Created by Hussain on 26/01/2015.
 */
public class BiPartiteMatchingTest {

    @Test
    public void testAllocateTasks(){
        final Skill java = new Skill("Java",1);
        final Skill uml = new Skill("UML",1);

        LinkedHashSet<Skill> e1SkillSet = new LinkedHashSet<Skill>(){{
            add(java); add(uml);
        }};

        LinkedHashSet<Skill> e2SkillSet = new LinkedHashSet<Skill>(){{
            add(java);
        }};

        Node<Employee> e1 = new Node<>(new Employee(1,"e1",e1SkillSet,0), NodeType.EMPLOYEE);
        Node<Employee> e2 = new Node<>(new Employee(2,"e2",e2SkillSet,0),NodeType.EMPLOYEE);

        Node<Task> t1 = new Node<>(new Task(3,"T1",100,1L,1L,false,e1SkillSet),NodeType.TASK);
        Node<Task> t2 = new Node<>(new Task(4,"T2",102,1L,1L,false,e2SkillSet),NodeType.TASK);

        Graph<Node<Employee>,Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);

        testGraph.addTaskNode(t1);
        testGraph.addTaskNode(t2);

        TaskAllocationMethod testTaskAllocationMethod = new BiPartiteMatching();
        testTaskAllocationMethod.allocateTasks(testGraph);

        assertTrue(testGraph.getMappedTask(e1).size() == 2);//Check that E1 has 2 tasks matched up with them
        assertTrue(testGraph.getMappedTask(e2).size() == 1);//Check that E2 has 1 task matched up with them
    }

    @Test
    public void testBiPartiteMatching(){
        Node<Employee> e1 = new Node<>(new Employee(1,"e1",null,0), NodeType.EMPLOYEE);
        Node<Employee> e2 = new Node<>(new Employee(2,"e2",null,0),NodeType.EMPLOYEE);

        Node<Task> t1 = new Node<>(new Task(3,"T1",100,1L,1L,false,null),NodeType.TASK);
        Node<Task> t2 = new Node<>(new Task(4,"T2",102,1L,1L,false,null),NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);

        testGraph.addTaskNode(t1);
        testGraph.addTaskNode(t2);

        testGraph.addEdge(e1,t1);
        testGraph.addEdge(e1,t2);
        testGraph.addEdge(e2,t2);

        BiPartiteMatching biPartiteMatching = new BiPartiteMatching();
        biPartiteMatching.biPartiteMatching(testGraph);

    }
}
