package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Hussain on 18/11/2014.
 */
public class TaskAllocationMethodTest {

    @Test
    public void testCheckSkillsMatchValid(){
        GreedyCost taskAllocation = new GreedyCost();
        Skill java = new Skill("java",1);
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(java);
        Employee e = new Employee(1,"Test",skillSet,0);
        Task t = new Task(1,"t1",1,1,1,false,skillSet);
        assertTrue(taskAllocation.checkSkillsMatch(e,t));
    }

    @Test
    public void testCheckSkillsMatchInValid(){
        GreedyCost taskAllocation = new GreedyCost();
        Skill uml = new Skill("UML",1);
        Skill java = new Skill("java",1);
        Set<Skill> employeeSkillSet = new HashSet<>();
        Set<Skill> taskSkillSet = new HashSet<>();
        employeeSkillSet.add(uml);
        taskSkillSet.add(java);

        Employee e = new Employee(1, "", employeeSkillSet, 0);
        Task t = new Task(1, "", 1, 1, 1, false, taskSkillSet);

        assertFalse(taskAllocation.checkSkillsMatch(e,t));
    }
}
