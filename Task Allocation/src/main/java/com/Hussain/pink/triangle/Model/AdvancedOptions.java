package com.Hussain.pink.triangle.Model;

/**
 * This class will be used to store the
 * advanced options that the user will be choose
 * when they would like to see a allocation
 *
 * Created by Hussain on 30/11/2014.
 */
public class AdvancedOptions {
    private static boolean CHECK_IF_EMPLOYEES_ARE_ASSIGNED_TO_TASKS = false;
    private static OrderType EMPLOYEE_ORDER = OrderType.NAME_ALPHABETICAL; //This is the default ordering

    private static OrderType TASKS_ORDER = OrderType.NONE; //This is the default ordering
    private static boolean GROUP_TASKS_BY_PROJECT = false;


    /**
     * Gets the property whether the query should check
     * if the employee has been assigned to a task
     * @return True if the query should check if the employee has been
     * assigned to a task, false otherwise
     */
    public static boolean checkIfEmployeesAreAssignedToTasks() {
        return CHECK_IF_EMPLOYEES_ARE_ASSIGNED_TO_TASKS;
    }

    /**
     * Sets the property to check if employees have been
     * assigned to a task
     * @param checkIfEmployeesAreAssignedToTasks True if the user has decided
     *                                           that they would like to check if the employee has been assigned
     *                                           to a task, false otherwise
     */
    public static void setCheckIfEmployeesAreAssignedToTasks(boolean checkIfEmployeesAreAssignedToTasks) {
        CHECK_IF_EMPLOYEES_ARE_ASSIGNED_TO_TASKS = checkIfEmployeesAreAssignedToTasks;
    }

    /**
     * Gets the order of employees that should be used in the query
     * @return Order of the employees for the query
     */
    public static OrderType getEmployeeOrder() {
        return EMPLOYEE_ORDER;
    }

    /**
     * Sets the employee order for the query
     * @param employeeOrder The order type the user has chosen
     */
    public static void setEmployeeOrder(OrderType employeeOrder) {
        EMPLOYEE_ORDER = employeeOrder;
    }

    /**
     * Gets the order of the tasks that should be used in the query
     * @return Order of the tasks for the query
     */
    public static OrderType getTasksOrder() {
        return TASKS_ORDER;
    }

    /**
     * Sets the property for the tasks order
     * @param tasksOrder The order of the tasks the user has chosen
     */
    public static void setTasksOrder(OrderType tasksOrder) {
        TASKS_ORDER = tasksOrder;
    }

    /**
     * Gets the property whether the tasks should be grouped by
     * their projects
     * @return True if the user would like to order the tasks by the projects,
     * false otherwise
     */
    public static boolean groupTasksByProject() {
        return GROUP_TASKS_BY_PROJECT;
    }

    /**
     * Sets the property whether the tasks should be ordered by their projects
     * @param groupTasksByProject True if you would like to group the tasks by their project
     *                            false otherwise
     */
    public static void setGroupTasksByProject(boolean groupTasksByProject) {
        GROUP_TASKS_BY_PROJECT = groupTasksByProject;
    }
}
