package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class EuclideanHeuristicTest {


    @Test
    public void testFindBestMatchEmployee() {
        final Skill java = new Skill("Java",1);
        final Skill java4 = new Skill("Java",4);
        final Skill uml = new Skill("UML",2);
        final Skill iOS = new Skill("iOS",4);

        LinkedHashSet<Skill> e1SkillSet = new LinkedHashSet<Skill>(){{
            add(java);
        }};

        LinkedHashSet<Skill> e2SkillSet = new LinkedHashSet<Skill>(){{
            add(java4); add(iOS);
        }};

        LinkedHashSet<Skill> e3SkillSet = new LinkedHashSet<Skill>(){{
            add(java); add(uml);
        }};

        Employee e1 = new Employee(1,"E1",e1SkillSet,0);
        Employee e2 = new Employee(2,"E2",e2SkillSet,0);
        Employee e3 = new Employee(3,"E3",e3SkillSet,0);

        List<String> listOfApplicableEmployees = new ArrayList<String>(){{
            add("E1"); add("E2"); add("E3");
        }};

        BiPartiteGraph biPartiteGraph = new BiPartiteGraph();
        biPartiteGraph.addEmployeeToIndexMap(e1);
        biPartiteGraph.addEmployeeToIndexMap(e2);
        biPartiteGraph.addEmployeeToIndexMap(e3);

        assertEquals("E1", EuclideanHeuristic.findBestMatchedEmployee(listOfApplicableEmployees,biPartiteGraph));
    }


}