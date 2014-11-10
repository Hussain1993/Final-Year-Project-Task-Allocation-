package com.Hussain.pink.triangle.Organisation;

import java.util.List;

/**
 * Created by Hussain on 10/11/2014.
 *
 * select employees.id,employees.first_name,employees.last_name,
 * group_concat(skills.skill),employees.cost,employee_skills.PROFICIENCY from employee_skills
 * join employees on employee_skills.employee_id = employees.id join skills on employee_skills.skill_id=skills.id
 * group by employees.id;
 */
public class Employee {

    private final int id;
    private final String name;
    private final List<Skill> skills;
    private final double cost;

    public Employee(int id, String firstName, String lastName, List<Skill> skills, double cost){

        this.id = id;
        this.name = firstName + " " + lastName;
        this.skills = skills;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public double getCost() {
        return cost;
    }
}
