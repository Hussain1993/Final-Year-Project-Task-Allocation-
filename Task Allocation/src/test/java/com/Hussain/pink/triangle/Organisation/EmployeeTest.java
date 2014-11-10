package com.Hussain.pink.triangle.Organisation;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class EmployeeTest {

    private static Employee employee;
    private static Skill java = new Skill("Java",1);
    private static Skill uml = new Skill("UML",2);

    @BeforeClass
    public static void setUp(){
        List<Skill> skills = new ArrayList<>();
        skills.add(java);
        skills.add(uml);
        employee = new Employee(1, "Test", "One", skills, 10);
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
        Skill [] skillsArray = employee.getSkills().toArray(new Skill [] {});
        assertArrayEquals(skillsArray,new Skill [] {java,uml});
    }

    @Test
    public void testGetCost() {
        assertEquals(employee.getCost(),10,0);
    }
}