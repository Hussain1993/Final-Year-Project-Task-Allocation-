package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;

/**
 * This heuristic should only be used for
 * the Greedy Task Allocation method
 *
 *
 * Created by Hussain on 09/02/2015.
 */
public class EuclideanHeuristic {

    /**
     *This function will take a list of all applicable employees
     * for a task and will find the best employee suited for the task based on their
     * skills and the euclidean distance they are for the task
     * @param employeesApplicableForTask A list of all applicable employees for a given task
     * @return A node object representing the best matched employee for the task.
     */
    public static Node<Employee> findBestMatchEmployee(ArrayList<Node<Employee>> employeesApplicableForTask){
        PriorityQueue<Employee> employeesPriorityQueue = new PriorityQueue<>();
        for (Node<Employee> employeeNode : employeesApplicableForTask)
        {
            Employee e = employeeNode.getObject();
            LinkedHashSet<Skill> skillSet = e.getSkills();
            int functionScore = 0;
            for (Skill skill : skillSet)
            {
                functionScore += Math.pow(skill.getProficiency(),2);
            }
            functionScore = (int) Math.sqrt(functionScore);
            e.setHeuristicFunctionScore(functionScore);
            employeesPriorityQueue.add(e);
        }
        return new Node<>(employeesPriorityQueue.remove(), NodeType.EMPLOYEE);
    }

}
