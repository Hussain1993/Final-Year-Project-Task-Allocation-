package com.Hussain.pink.triangle.Organisation;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmployeeTest {

    private static Employee employee;
    private static Skill java = new Skill("Java",1);
    private static Skill uml = new Skill("UML",2);
    private static Set<Skill> skillSet = new HashSet<>();

    @BeforeClass
    public static void setUp(){
        skillSet.add(java);
        skillSet.add(uml);
        employee = new Employee(1, "Test", "One", skillSet, 10);
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
        assertTrue(employee.equals(new Employee(1,"Test","One",skillSet,10)));

    }

    @Test
    public void testEqualsNotSameID() {
        assertFalse(employee.equals(new Employee(120,"Hussain","One",skillSet,10)));

    }
}