package com.Hussain.pink.triangle.Organisation;

import org.joda.time.LocalDate;

import java.util.LinkedHashSet;

/**
 * Created by Hussain on 10/11/2014.
 *
 * This class stores the attributes of a task
 * when they are being retrieved from the database
 *
 */
public class Task{

    private final int id;
    private final String taskName;
    private final Project project;
    private final LocalDate dateFrom;
    private final LocalDate dateTo;
    private final boolean completed;
    private final LinkedHashSet<Skill> skills;

    public Task(int id, String taskName,Project project,long dateFrom,long dateTo, boolean completed
    ,LinkedHashSet<Skill> skills){
        this.id = id;
        this.taskName = taskName;
        this.project = project;
        this.dateFrom = new LocalDate(dateFrom);
        this.dateTo = new LocalDate(dateTo);
        this.completed = completed;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public Project getProject() {
        return project;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public boolean isCompleted() {
        return completed;
    }

    public LinkedHashSet<Skill> getSkills() {
        return skills;
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
        if(!(other instanceof Task))
        {
            return false;
        }
        Task otherTask = (Task) other;
        return this.getId() == otherTask.getId();
    }

    @Override
    public String toString() {
        return "Task: " + this.getTaskName();
    }

    @Override
    public int hashCode(){
        int hash = 1;
        hash = hash * 17 + getId();
        hash = hash * 31 + getTaskName().hashCode();
        return hash;
    }
}
