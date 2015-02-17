package com.Hussain.pink.triangle.Organisation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProjectTest {

    @Test
    public void testGetProjectID() {
        Project p1 = new Project(1,"Windows Phone Game");
        assertEquals(1,p1.getProjectID());
    }

    @Test
    public void testGetProjectName() {
        Project p1 = new Project(1,"Windows Phone Game");
        assertEquals("Windows Phone Game",p1.getProjectName());
    }

    @Test
    public void testToString() {
        Project p1 = new Project(1,"Windows Phone Game");
        assertEquals("Project: Windows Phone Game",p1.toString());
    }

    @Test
    public void testEquals() {
        Project p1 = new Project(1,"Windows Phone Game");
        //noinspection ObjectEqualsNull
        assertFalse(p1.equals(null));
        //noinspection EqualsWithItself
        assertTrue(p1.equals(p1));
        //noinspection EqualsBetweenInconvertibleTypes
        assertFalse(p1.equals(new Employee(1,"",null,0)));
        Project p2 = new Project(1,"Windows Phone");
        assertTrue(p1.equals(p2));
        Project p3 = new Project(2,"iOS Game");
        assertFalse(p1.equals(p3));
    }

    @Test
    public void testHashCode() {
        Project p1 = new Project(1,"Windows Phone Game");
        assertEquals(-811117093, p1.hashCode());
    }
}