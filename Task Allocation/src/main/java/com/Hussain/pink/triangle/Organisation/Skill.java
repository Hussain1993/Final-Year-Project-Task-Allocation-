package com.Hussain.pink.triangle.Organisation;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Hussain on 10/11/2014.
 * This class will be used to store
 * the skills for an employee and a task
 */
public class Skill {

    private final String skill;
    private final int proficiency;

    public Skill(String skill, int proficiency){
        this.skill = skill;
        this.proficiency = proficiency;
    }

    public int getProficiency() {
        return proficiency;
    }

    public String getSkill() {
        return skill;
    }

    @Override
    public boolean equals(Object other){
        if(other == null)
        {
            return false;
        }
        if(other == this)
        {
            return true;
        }
        if(!(other instanceof Skill))
        {
            return false;
        }
        Skill skillToCheck = (Skill)other;
        if(StringUtils.equals(this.getSkill(),skillToCheck.getSkill()) && this.getProficiency() == skillToCheck.getProficiency())
        {
            return true;
        }
        return false;
    }
}
