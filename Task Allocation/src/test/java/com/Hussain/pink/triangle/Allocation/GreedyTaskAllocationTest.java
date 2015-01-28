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

        TaskAllocationMethod greedyMethod = new GreedyTaskAllocation();
        greedyMethod.allocateTasks(testGraph);//Allocate the employees and tasks within the graph

        assertTrue(testGraph.getEmployeeNodes().size() == 1);//Assert there is only one employee in the graph
        assertTrue(testGraph.getTaskNodes().size() == 1);//Assert there is only one task in the graph
        assertTrue(testGraph.getMappedTask(e1).size() == 1);//Assert there is only one mapped task for the employee
        assertTrue(testGraph.hasRelationship(e1,t1));//Assert there is a mapping between the employee and task
    }
}