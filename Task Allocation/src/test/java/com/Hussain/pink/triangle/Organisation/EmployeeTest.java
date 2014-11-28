package com.Hussain.pink.triangle.Organisation;

import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedHashSet;

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
    public void testGetDateFrom() {
        LocalDate d = new LocalDate(1);
        Employee e = new Employee(1,null,null,0,1,1,1);
        assertEquals(d,e.getDateFrom());
    }

    @Test
    public void testGetDateTo() {
        LocalDate d = new LocalDate(1);
        Employee e = new Employee(1,null,null,0,1,1,1);

        assertEquals(d,e.getDateTo());
    }

    @Test
    public void testGetEmployeeType() {
        Employee unassignedEmployee = new Employee(1,null,null,1);
        Employee assignedEmployee = new Employee(1,null,null,0,1,1,1);

        assertEquals(EmployeeType.NOT_ASSIGNED_TASK,unassignedEmployee.getEmployeeType());
        assertEquals(EmployeeType.ASSIGNED_TASK, assignedEmployee.getEmployeeType());
    }
}