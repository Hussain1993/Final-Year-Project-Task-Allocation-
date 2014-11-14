package com.Hussain.pink.triangle.Organisation;

import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class TaskTest {
    private static Task task;

    private static Skill java = new Skill("Java",1);
    private static Skill uml = new Skill("UML",2);

    private static Set<Skill> skillSet = new HashSet<>();

    @BeforeClass
    public static void setUp() throws Exception {
        skillSet.add(java);
        skillSet.add(uml);

        Date dateFrom = new Date(1402012800000L);
        Date dateTo = new Date(1404691200000L);

        task = new Task(1,"TaskOne",123,dateFrom.getTime(),dateTo.getTime(),false,skillSet);
    }

    @Test
    public void testGetId() {
        assertEquals(task.getId(),1);
    }

    @Test
    public void testGetTaskName() {
        assertEquals(task.getTaskName(),"TaskOne");
    }

    @Test
    public void testGetProjectId() {
        assertEquals(task.getProjectId(),123);
    }

    @Test
    public void testGetDateFrom() {
        LocalDate dateFrom = new LocalDate(1402012800000L);
        assertEquals(task.getDateFrom(),dateFrom);
    }

    @Test
    public void testGetDateTo() {
        LocalDate dateTo = new LocalDate(1404691200000L);
        assertEquals(task.getDateTo(),dateTo);
    }

    @Test
    public void testIsCompleted() {
        assertFalse(task.isCompleted());
    }

    @Test
    public void testGetSkills() {
        assertEquals(task.getSkills(),skillSet);
    }

    @Test
    public void testEqualsNullObject() {
        assertFalse(task.equals(null));
    }

    @Test
    public void testEqualsSameObject() {
        assertTrue(task.equals(task));
    }

    @Test
    public void testEqualsNotSameObject() {
        assertFalse(task.equals("Test"));
    }

    @Test
    public void testEqualsValid() {
        assertTrue(task.equals(new Task(1,"TaskOne",1,System.currentTimeMillis(),System.currentTimeMillis(),false,new HashSet<Skill>())));

    }

    @Test
    public void testEqualsNotValid() {
        assertFalse(task.equals(new Task(2,"Test",2,System.currentTimeMillis(),System.currentTimeMillis(),false,new HashSet<Skill>())));

    }
}