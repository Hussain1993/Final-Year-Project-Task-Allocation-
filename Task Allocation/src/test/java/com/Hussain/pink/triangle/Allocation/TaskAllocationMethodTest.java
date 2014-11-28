package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.LinkedHashSet;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hussain on 18/11/2014.
 */
public class TaskAllocationMethodTest {

    @Test
    public void testCheckSkillsMatchValid(){
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Skill java = new Skill("java",1);
        LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();
        skillSet.add(java);
        Employee e = new Employee(1,"Test",skillSet,0);
        Task t = new Task(1,"t1",1,1,1,false,skillSet);
        assertTrue(taskAllocation.checkSkillsMatch(e,t));
    }

    @Test
    public void testCheckSkillsMatchInValid(){
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Skill uml = new Skill("UML",1);
        Skill java = new Skill("java",1);
        LinkedHashSet<Skill> employeeSkillSet = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkillSet = new LinkedHashSet<>();
        employeeSkillSet.add(uml);
        taskSkillSet.add(java);

        Employee e = new Employee(1, "", employeeSkillSet, 0);
        Task t = new Task(1, "", 1, 1, 1, false, taskSkillSet);

        assertFalse(taskAllocation.checkSkillsMatch(e,t));
    }

    @Test
    public void testCheckSkillsMatchOrder() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Skill uml = new Skill("UML",2);
        Skill java = new Skill("Java",1);

        LinkedHashSet<Skill> employeeSkillSet = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkillSet = new LinkedHashSet<>();

        employeeSkillSet.add(uml);
        employeeSkillSet.add(java);

        taskSkillSet.add(uml);
        taskSkillSet.add(java);

        Employee e = new Employee(1,"",employeeSkillSet,0);
        Task t = new Task(1,"",1,1,1,false,taskSkillSet);

        assertTrue(taskAllocation.checkSkillsMatch(e,t));

    }

    @Test
    public void testCheckSkillsMatchEmployeeHasMoreSkills() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();

        Skill java = new Skill("Java",1);
        Skill uml = new Skill("UML",1);
        Skill windowsPhone = new Skill("Windows Phone",1);

        LinkedHashSet<Skill> employeeSkills = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkills = new LinkedHashSet<>();

        employeeSkills.add(java);
        employeeSkills.add(uml);
        employeeSkills.add(windowsPhone);

        taskSkills.add(java);
        taskSkills.add(uml);

        Employee e = new Employee(1,null,employeeSkills,0);
        Task t = new Task(1, null, 1, 1, 1, false, taskSkills);

        assertTrue(taskAllocation.checkSkillsMatch(e,t));
    }

    @Test
    public void testCheckSkillsMatchEmployeeIsMoreSkilled() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();

        Skill eSkill1 = new Skill("Java",2);
        Skill uml = new Skill("UML", 1);
        Skill windowsPhone = new Skill("Windows Phone",1);

        Skill taskJavaSkill = new Skill("Java",1);

        LinkedHashSet<Skill> employeeSkills = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkills = new LinkedHashSet<>();

        employeeSkills.add(eSkill1);
        employeeSkills.add(uml);
        employeeSkills.add(windowsPhone);

        taskSkills.add(taskJavaSkill);
        taskSkills.add(uml);

        Employee e = new Employee(1, null, employeeSkills, 0);
        Task t = new Task(1, null, 1, 1, 1, false, taskSkills);

        assertTrue(taskAllocation.checkSkillsMatch(e,t));

    }

    @Test
    public void testBuildSkillSet() {
        GreedyTaskAllocation taskAllocation  = new GreedyTaskAllocation();
        String skills = "Java,UML";
        String proficiency = "1,2";

        Skill java = new Skill("Java",1);
        Skill uml = new Skill("UML",2);

        LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();
        skillSet.add(java);
        skillSet.add(uml);

        Skill[] skillArray = skillSet.toArray(new Skill[skillSet.size()]);

        LinkedHashSet<Skill> skillLinkedHashSet = taskAllocation.buildSkillSet(skills, proficiency);
        assertArrayEquals(skillArray, skillLinkedHashSet.toArray(new Skill[skillLinkedHashSet.size()]));

    }

    @Test
    public void testCheckEmployeeAvailableForTask_TimeRangeDoesOverlap() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Employee e = new Employee(1,null,null,0,1073779200000L,1100131200000L,0);

        Task t = new Task(1,null,1,1089500400000L,1089500400000L,false,null);

        assertFalse(taskAllocation.checkEmployeeAvailableForTask(e, t));
    }

    @Test
    public void testCheckEmployeeAvailableForTask_TimeRangeDoesNotOverlap() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Employee e = new Employee(1,null,null,0,1073779200000L,1100131200000L,0);

        Task t = new Task(1,null,1,1105401600000L,1108080000000L,false,null);

        assertTrue(taskAllocation.checkEmployeeAvailableForTask(e,t));

    }
}
