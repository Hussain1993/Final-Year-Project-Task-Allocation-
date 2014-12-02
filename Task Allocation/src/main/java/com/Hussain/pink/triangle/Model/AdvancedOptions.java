package com.Hussain.pink.triangle.Model;

/**
 * Created by Hussain on 30/11/2014.
 */
public class AdvancedOptions {
    private static boolean CHECK_IF_EMPLOYEES_ARE_ASSIGNED_TO_TASKS = false;
    private static OrderType EMPLOYEE_ORDER = OrderType.NAME_ALPHABETICAL;

    private static OrderType TASKS_ORDER = OrderType.NONE;
    private static boolean GROUP_TASKS_BY_PROJECT = false;


    public static boolean checkIfEmployeesAreAssignedToTasks() {
        return CHECK_IF_EMPLOYEES_ARE_ASSIGNED_TO_TASKS;
    }

    public static void setCheckIfEmployeesAreAssignedToTasks(boolean checkIfEmployeesAreAssignedToTasks) {
        CHECK_IF_EMPLOYEES_ARE_ASSIGNED_TO_TASKS = checkIfEmployeesAreAssignedToTasks;
    }

    public static OrderType getEmployeeOrder() {
        return EMPLOYEE_ORDER;
    }

    public static void setEmployeeOrder(OrderType employeeOrder) {
        EMPLOYEE_ORDER = employeeOrder;
    }

    public static OrderType getTasksOrder() {
        return TASKS_ORDER;
    }

    public static void setTasksOrder(OrderType tasksOrder) {
        TASKS_ORDER = tasksOrder;
    }

    public static boolean groupTasksByProject() {
        return GROUP_TASKS_BY_PROJECT;
    }

    public static void setGroupTasksByProject(boolean groupTasksByProject) {
        GROUP_TASKS_BY_PROJECT = groupTasksByProject;
    }
}
