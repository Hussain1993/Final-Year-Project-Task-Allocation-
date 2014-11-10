package com.Hussain.pink.triangle.Organisation;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testEqualsNull(){
        assertFalse(skill.equals(null));
    }

    @Test
    public void testEqualsSameObject() throws Exception {
        assertTrue(skill.equals(skill));
    }

    @Test
    public void testEqualsDifferentObject() {
        assertFalse(skill.equals(new Object()));
    }

    @Test
    public void testEqualsValid(){
        Skill s = new Skill("Java", 1);
        assertTrue(skill.equals(s));
    }

    @Test
    public void testEqualsNotValid() {
        Skill uml = new Skill("UML", 2);
        assertFalse(skill.equals(uml));
    }
}