package com.Hussain.pink.triangle.Organisation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GroupTaskTest {

    @Test
    public void testAddNewTaskToGroup() {
        Project p1 = new Project(1,"Windows Phone");
        Project p2 = new Project(2,"iOS Game");
        Project p3 = new Project(1,"Windows Phone");

        GroupTask.addNewTaskToGroup(p1);
        GroupTask.addNewTaskToGroup(p2);
        GroupTask.addNewTaskToGroup(p3);

        assertEquals(2, GroupTask.getNumberOfMappedTasksForProject(p1));//There should be two tasks mapped to this project
        assertEquals(1,GroupTask.getNumberOfMappedTasksForProject(p2));//There should only one task mapped to this project
    }

    @Test
    public void testRemoveTaskFromGroup() throws Exception{
        Project p1 = new Project(1100,"Windows Phone");
        Project p2 = new Project(200,"iOS Game");
        Project p3 = new Project(1100,"Windows Phone");

        GroupTask.addNewTaskToGroup(p1);
        GroupTask.addNewTaskToGroup(p2);
        GroupTask.addNewTaskToGroup(p3);

        assertEquals(2, GroupTask.getNumberOfMappedTasksForProject(p1));
        assertEquals(1,GroupTask.getNumberOfMappedTasksForProject(p2));

        GroupTask.removeTaskFromGroup(p1);

        assertEquals(1,GroupTask.getNumberOfMappedTasksForProject(p1));
    }
}