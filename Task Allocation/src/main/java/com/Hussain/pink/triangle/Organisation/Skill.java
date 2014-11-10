package com.Hussain.pink.triangle.Organisation;

/**
 * Created by Hussain on 10/11/2014.
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
}
