package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;

/**
 * Created by Hussain on 09/02/2015.
 */
public class EuclideanHeuristic {

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
