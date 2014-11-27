package com.Hussain.pink.triangle.Organisation;

import org.joda.time.LocalDate;

import java.util.LinkedHashSet;

/**
 * Created by Hussain on 27/11/2014.
 */
public class EmployeeAssignedTask extends Employee {
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int taskAssignedID;


    public EmployeeAssignedTask(int id, String name, LinkedHashSet<Skill> skills, double cost, long dateFrom,long dateTo, int taskAssignedID) {
        super(id, name, skills, cost);

        this.dateFrom = new LocalDate(dateFrom);
        this.dateTo = new LocalDate(dateTo);
        this.taskAssignedID = taskAssignedID;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public int getTaskAssignedID() {
        return taskAssignedID;
    }
}
