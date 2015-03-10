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
     * This method will find the best matched employee and keep an
     * order of the employees within a priority queue
     * @param listOfApplicableEmployees This is the list of all the applicable
     *                                  employees for the given task
     * @param biPartiteGraph The graph so that we can get the object from the name
     * @return A priority queue of all applicable employees, sorted by the best matched
     * to worse matched
     */
    public static PriorityQueue<Employee> findBestMatchedEmployee(List<String> listOfApplicableEmployees, BiPartiteGraph biPartiteGraph){
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
        return employeePriorityQueue;
    }

}
