package com.Hussain.pink.triangle.Model;

/**
 * Created by Hussain on 30/11/2014.
 */
public class AdvancedOptions {
    private static boolean EMPLOYEE_ASSIGNED_TASKS = false;

    private static String TASKS_ORDER = "-";


    public static void setEmployeeAssignedTasks(boolean employeeAssignedTasks) {
        EMPLOYEE_ASSIGNED_TASKS = employeeAssignedTasks;
    }

    public static void setTasksOrder(String tasksOrder) {
        TASKS_ORDER = tasksOrder;
    }

    public static boolean isEmployeeAssignedTasks() {
        return EMPLOYEE_ASSIGNED_TASKS;
    }

    public static String getTasksOrder() {
        return TASKS_ORDER;
    }
}
