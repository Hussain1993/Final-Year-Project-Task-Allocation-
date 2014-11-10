package com.Hussain.pink.triangle.Organisation;

import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TaskTest {
    private static Task task;

    private static Skill java = new Skill("Java",1);
    private static Skill uml = new Skill("UML",2);

    @BeforeClass
    public static void setUp() throws Exception {
        List<Skill> skillsList = new ArrayList<>();
        skillsList.add(java);
        skillsList.add(uml);

        Date dateFrom = new Date(1402012800000L);
        Date dateTo = new Date(1404691200000L);

        task = new Task(1,"TaskOne",123,dateFrom.getTime(),dateTo.getTime(),false,skillsList);
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
        System.out.println(dateFrom);
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
        Skill [] skills = task.getSkills().toArray(new Skill [] {});
        assertArrayEquals(skills,new Skill [] {java,uml});
    }
}