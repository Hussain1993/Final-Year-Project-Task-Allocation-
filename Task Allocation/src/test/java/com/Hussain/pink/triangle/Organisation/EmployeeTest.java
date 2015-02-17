package com.Hussain.pink.triangle.Organisation;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class EmployeeTest {

    private static Employee employee;
    private static Skill java = new Skill("Java",1);
    private static Skill uml = new Skill("UML",2);
    private static LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();

    @BeforeClass
    public static void setUp(){
        skillSet.add(java);
        skillSet.add(uml);
        employee = new Employee(1, "Test One", skillSet, 10);
    }

    @Test
    public void testGetId() {
        assertEquals(employee.getId(),1);
    }

    @Test
    public void testGetName()  {
        assertEquals(employee.getName(),"Test One");
    }

    @Test
    public void testGetSkills()  {
        assertEquals(employee.getSkills(), skillSet);
    }

    @Test
    public void testGetCost() {
        assertEquals(employee.getCost(),10,0);
    }

    @Test
    public void testEqualsNullObject() {
        assertFalse(employee.equals(null));
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(employee.equals(employee));
    }

    @Test
    public void testEqualsOtherObject() {
        assertFalse(employee.equals("Test"));
    }

    @Test
    public void testEqualsValid() {
        assertTrue(employee.equals(new Employee(1,"Test",skillSet,10)));

    }

    @Test
    public void testEqualsNotSameID() {
        assertFalse(employee.equals(new Employee(120,"Hussain",skillSet,10)));

    }

    @Test
    public void testGetEmployeeType() {
        Employee employeeNotAssigned = new Employee(1,null,null,1);
        assertEquals(EmployeeType.NOT_ASSIGNED_TASK, employeeNotAssigned.getEmployeeType());

        Task t = new Task(1,null,null,1,1,false,null);

        Employee employeeAssignedATask = new Employee(1,null,null,1,t);

        assertEquals(EmployeeType.ASSIGNED_TASK, employeeAssignedATask.getEmployeeType());
    }

    @Test
    public void testGetSetHeuristicFunctionScore() {
        Employee e = new Employee(1,"",null,0);

        e.setHeuristicFunctionScore(10);

        assertEquals(10,e.getHeuristicFunctionScore());

    }

    @Test
    public void testCompare(){
        Employee e1 = new Employee(1,"E1",null,0);
        Employee e2 = new Employee(2,"E2",null,0);

        e1.setHeuristicFunctionScore(1000);
        e2.setHeuristicFunctionScore(9);

        PriorityQueue<Employee> employeePriorityQueue = new PriorityQueue<>();

        employeePriorityQueue.add(e1);
        employeePriorityQueue.add(e2);

        assertEquals(e2,employeePriorityQueue.peek());
    }
}