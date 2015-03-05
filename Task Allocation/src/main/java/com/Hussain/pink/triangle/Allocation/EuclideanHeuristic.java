package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;

/**
 * This heuristic should only be used for
 * the Greedy Task Allocation method
 *
 * Created by Hussain on 09/02/2015.
 */
public class EuclideanHeuristic {

    /**
     * This finds the best match employee for a
     * given task, using a euclidean heuristic
     * @param listOfApplicableEmployees The list of all the applicable
     *                                  employees for the task
     * @param biPartiteGraph The bipartite graph so that we can get
     *                       the employee object mapped to the name
     * @return The name of the best matched employee for the task
     */
    public static String findBestMatchedEmployee(List<String> listOfApplicableEmployees, BiPartiteGraph biPartiteGraph){
        PriorityQueue<Employee> employeePriorityQueue = new PriorityQueue<>();
        for (String employeeName : listOfApplicableEmployees)
        {
            Employee employee = biPartiteGraph.getEmployeeByName(employeeName);
            LinkedHashSet<Skill> skillSetForEmployee = employee.getSkills();
            int functionScore = 0;
            for (Skill skill : skillSetForEmployee)
            {
                functionScore += Math.pow(skill.getProficiency(),2);
            }
            functionScore = (int) Math.sqrt(functionScore);
            employee.setHeuristicFunctionScore(functionScore);
            employeePriorityQueue.add(employee);
        }
        return employeePriorityQueue.remove().getName();
    }

}
