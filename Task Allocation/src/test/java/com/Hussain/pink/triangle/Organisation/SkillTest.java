package com.Hussain.pink.triangle.Organisation;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SkillTest {
    private static Skill skill;

    @BeforeClass
    public static void setUp() throws Exception {
        skill = new Skill("Java",1);
    }

    @Test
    public void testGetProficiency() throws Exception {
        assertEquals(skill.getProficiency(),1);
    }

    @Test
    public void testGetSkill() throws Exception {
        assertEquals(skill.getSkill(),"Java");
    }
}