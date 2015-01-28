package com.Hussain.pink.triangle.Organisation;

import java.util.LinkedHashSet;

/**
 * Created by Hussain on 10/11/2014.
 *
 * This class will be used to store the
 * attributes of an employee when they are being
 * retrieved from the database
 *
 */
public class Employee {

    private int id;
    private String name;
    private LinkedHashSet<Skill> skills;
    private double cost;


    private Task taskAssigned;

    /**
     * This constructor should be used when the employee has not
     * been assigned to a task previously
     * @param id Of the employee
     * @param name Of Employee
     * @param skills Of the Employee
     * @param cost Of the Employee
     */
    public Employee(int id, String name, LinkedHashSet<Skill> skills, double cost){

        this.id = id;
        this.name = name;
        this.skills = skills;
        this.cost = cost;
    }

    /**
     * This is the constructor that should be used when the employee has been assigned to a task
     * @param id Of the employee
     * @param name Of the employee
     * @param skills Of the employee
     * @param cost Of the employee
     * @param taskAssigned ID of the task that has been assigned
     */
    public Employee(int id, String name, LinkedHashSet<Skill> skills, double cost, Task taskAssigned){
        this(id, name, skills, cost);

        this.taskAssigned = taskAssigned;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LinkedHashSet<Skill> getSkills() {
        return skills;
    }

    public double getCost() {
        return cost;
    }

    public EmployeeType getEmployeeType(){
        if(this.taskAssigned == null)
        {
            return EmployeeType.NOT_ASSIGNED_TASK;
        }
        return EmployeeType.ASSIGNED_TASK;
    }

    public Task getTaskAssigned(){
        return this.taskAssigned;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null)
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }
        if(!(other instanceof Employee))
        {
            return false;
        }
        Employee otherEmployee = (Employee) other;
        return this.getId() == otherEmployee.getId();
    }

    @Override
    public String toString() {
        return "Employee: "+getName();
    }

    @Override
    public int hashCode(){
        int hash = 1;
        hash = hash * 17 + id;
        hash = hash * 31 + getName().hashCode();
        hash = hash * 5 + getEmployeeType().hashCode();
        return hash;
    }
}
