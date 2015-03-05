package com.Hussain.pink.triangle.Model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AdvancedOptionsTest {

    @Test
    public void testGetSetCheckIfEmployeesAreAssignedToTasks(){
        assertFalse(AdvancedOptions.checkIfEmployeesAreAssignedToTasks());

        AdvancedOptions.setCheckIfEmployeesAreAssignedToTasks(true);

        assertTrue(AdvancedOptions.checkIfEmployeesAreAssignedToTasks());
    }

    @Test
    public void testGetSetEmployeeOrder() {
        assertEquals(OrderType.NAME_ALPHABETICAL, AdvancedOptions.getEmployeeOrder());

        AdvancedOptions.setEmployeeOrder(OrderType.COST_ASCENDING);

        assertEquals(OrderType.COST_ASCENDING, AdvancedOptions.getEmployeeOrder());
    }

    @Test
    public void testGetSetTaskOrder() {
        assertEquals(OrderType.NONE, AdvancedOptions.getTasksOrder());

        AdvancedOptions.setTasksOrder(OrderType.NAME_ALPHABETICAL);

        assertEquals(OrderType.NAME_ALPHABETICAL,AdvancedOptions.getTasksOrder());
    }

    @Test
    public void testGetSetGroupTasksByProject() {
        assertFalse(AdvancedOptions.groupTasksByProject());

        AdvancedOptions.setGroupTasksByProject(true);

        assertTrue(AdvancedOptions.groupTasksByProject());
    }

    @Test
    public void testGetSetUseHeuristic() {
        AdvancedOptions.setUseHeuristic(false);

        assertFalse(AdvancedOptions.getUseHeuristic());

        AdvancedOptions.setUseHeuristic(true);

        assertTrue(AdvancedOptions.getUseHeuristic());

    }
}