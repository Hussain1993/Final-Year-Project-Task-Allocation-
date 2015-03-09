package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * A specific task allocation scheme, in this
 * instance it is a greedy way of allocating the tasks the employees,
 * where this will assign the employee to the first task that they have
 * the right skills for and this task cannot be further assigned to anyone else
 *
 * Created by Hussain on 21/11/2014.
 */
public class GreedyTaskAllocation  extends TaskAllocationMethod{

    @Override
    public Matching<String> allocateTasks(BiPartiteGraph biPartiteGraph) {
        Set<String> employeeNodes = biPartiteGraph.getEmployeeNodes();
        Set<String> taskNodes = biPartiteGraph.getTaskNodes();
        unmatchedEmployees = new LinkedHashSet<>(employeeNodes);
        unmatchedTasks = new LinkedHashSet<>(taskNodes);
        if(AdvancedOptions.getUseHeuristic())//They would like to use the heuristic
        {
            greedyHeuristic(taskNodes, biPartiteGraph);
        }
        else
        {
            matching = GreedyMatching.greedy(unmatchedEmployees,unmatchedTasks,employeeNodes,biPartiteGraph);
        }
        logUnmatchedEmployeesAndTasks();
        return matching;
    }

    /**
     * This is greedy heuristic algorithm
     * @param taskNodes These are all the task nodes
     * @param biPartiteGraph Used to get object from their name
     */
    private void greedyHeuristic(Set<String> taskNodes, BiPartiteGraph biPartiteGraph){
        for (String taskName : taskNodes)
        {
            Task task = biPartiteGraph.getTaskByName(taskName);
            List<String> listOfApplicableEmployees = biPartiteGraph.getAdjacentNodes(taskName);
            if (listOfApplicableEmployees.size() > 1)
            {
                PriorityQueue<Employee> sortedEmployees = EuclideanHeuristic.findBestMatchedEmployee(listOfApplicableEmployees, biPartiteGraph);
                while(!sortedEmployees.isEmpty())
                {
                    Employee e = sortedEmployees.poll();
                    if(unmatchedEmployees.contains(e.getName()))//We have found an unmatched employee from the heuristic function
                    {
                        unmatchedEmployees.remove(e.getName());
                        unmatchedTasks.remove(taskName);
                        matching.addMatching(e.getName(),taskName);
                        break;//Move onto the next task
                    }
                }
            }
            else if (listOfApplicableEmployees.size() == 1)
            {
                if(unmatchedEmployees.containsAll(listOfApplicableEmployees))
                {
                    unmatchedEmployees.remove(listOfApplicableEmployees.get(0));//There is only one element in the set
                    unmatchedTasks.remove(taskName);
                    matching.addMatching(listOfApplicableEmployees.get(0), taskName);
                    GreedyMatching.processGroupTask(task);
                }
            }
        }
    }
}
