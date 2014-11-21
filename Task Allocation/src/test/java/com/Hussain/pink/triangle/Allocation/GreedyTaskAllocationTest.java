package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GreedyTaskAllocationTest {

    @Test
    public void testAllocateTasks() {
        Map<Employee,Task> actualAllocation = new HashMap<>();

        Skill java = new Skill("Java",1);
        Skill uml = new Skill("UML",2);

        LinkedHashSet<Skill> employeeSkillSet1 = new LinkedHashSet<>();
        LinkedHashSet<Skill> employeeSkillSet2 = new LinkedHashSet<>();

        LinkedHashSet<Skill> taskSkillSet = new LinkedHashSet<>();
        taskSkillSet.add(java);
        taskSkillSet.add(uml);

        employeeSkillSet1.add(java);

        employeeSkillSet2.add(java);
        employeeSkillSet2.add(uml);

        Employee e1 = new Employee(1,"Test",employeeSkillSet2,0);
        Employee e2 = new Employee(2,"Test",employeeSkillSet1,0);
        Employee e3 = new Employee(3,"Test",employeeSkillSet1,0);
        Employee e4 = new Employee(4,"Test",employeeSkillSet1,0);

        Task t = new Task(1,"T1",1,1,1,false,taskSkillSet);

        actualAllocation.put(e1,t);

        Graph<Employee, Task> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);
        testGraph.addEmployeeNode(e3);
        testGraph.addEmployeeNode(e4);

        testGraph.addTaskNode(t);

        testGraph.addEdge(e1,t);

        GreedyTaskAllocation allocationMethod = new GreedyTaskAllocation();

        allocationMethod.allocateTasks(testGraph);

        assertTrue(CollectionUtils.isEqualCollection(actualAllocation.entrySet(),testGraph.getEmployeeToTaskMapping().entrySet()));
    }

    @Test
    public void testHasTaskBeenAssigned() {
        Skill java = new Skill("Java",1);
        Skill uml = new Skill("UML",2);

        LinkedHashSet<Skill> employeeSkillSet1 = new LinkedHashSet<>();
        LinkedHashSet<Skill> employeeSkillSet2 = new LinkedHashSet<>();

        LinkedHashSet<Skill> taskSkillSet = new LinkedHashSet<>();
        taskSkillSet.add(java);
        taskSkillSet.add(uml);

        employeeSkillSet1.add(java);

        employeeSkillSet2.add(java);
        employeeSkillSet2.add(uml);

        Employee e1 = new Employee(1,"Test",employeeSkillSet2,0);
        Employee e2 = new Employee(2,"Test",employeeSkillSet1,0);
        Employee e3 = new Employee(3,"Test",employeeSkillSet1,0);
        Employee e4 = new Employee(4,"Test",employeeSkillSet1,0);

        Task t = new Task(1,"T1",1,1,1,false,taskSkillSet);

        Graph<Employee, Task> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);
        testGraph.addEmployeeNode(e3);
        testGraph.addEmployeeNode(e4);

        testGraph.addTaskNode(t);

        testGraph.addEdge(e1,t);

        GreedyTaskAllocation allocationMethod = new GreedyTaskAllocation();

        assertTrue(allocationMethod.hasTaskBeenAssigned(testGraph,t));

        Task newTask = new Task(2,"",1,1,1,false,null);

        assertFalse(allocationMethod.hasTaskBeenAssigned(testGraph,newTask));

    }


}