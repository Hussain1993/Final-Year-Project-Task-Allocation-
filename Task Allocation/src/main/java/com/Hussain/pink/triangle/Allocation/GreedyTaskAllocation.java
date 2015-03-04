package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.GroupTask;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.LinkedHashSet;
import java.util.List;
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
            greedy(employeeNodes,biPartiteGraph);
        }
        logUnmatchedEmployeesAndTasks();
        return matching;
    }

    private void greedyHeuristic(Set<String> taskNodes, BiPartiteGraph biPartiteGraph){
        for (String taskName : taskNodes)
        {
            Task task = biPartiteGraph.getTaskByName(taskName);
            List<String> listOfApplicableEmployees = biPartiteGraph.getAdjacentNodes(taskName);
            if (listOfApplicableEmployees.size() > 1)
            {
                String bestMatchedEmployee = EuclideanHeuristic.findBestMatchedEmployee(listOfApplicableEmployees, biPartiteGraph);
                if(unmatchedEmployees.contains(bestMatchedEmployee))
                {
                    unmatchedEmployees.remove(bestMatchedEmployee);
                    unmatchedTasks.remove(taskName);
                    matching.addMatching(bestMatchedEmployee, taskName);
                    processGroupTask(task);
                }
            }
            else if (listOfApplicableEmployees.size() == 1)
            {
                if(unmatchedEmployees.containsAll(listOfApplicableEmployees))
                {
                    unmatchedEmployees.remove(listOfApplicableEmployees.get(0));//There is only one element in the set
                    unmatchedTasks.remove(taskName);
                    matching.addMatching(listOfApplicableEmployees.get(0), taskName);
                    processGroupTask(task);
                }
            }
        }
    }

    public void greedy(Set<String> employeeNodes, BiPartiteGraph biPartiteGraph){
        LinkedHashSet<String> tasksNodesWeHaveAssigned = new LinkedHashSet<>();
        for(String employeeName : employeeNodes)
        {
            for (String taskName : biPartiteGraph.getAdjacentNodes(employeeName))
            {
                if(!tasksNodesWeHaveAssigned.contains(taskName))
                {
                    tasksNodesWeHaveAssigned.add(taskName);
                    unmatchedEmployees.remove(employeeName);
                    unmatchedTasks.remove(taskName);
                    matching.addMatching(employeeName,taskName);
                    processGroupTask(biPartiteGraph.getTaskByName(taskName));
                    break;//Move onto the next employee
                }
            }
        }
    }

    private void processGroupTask(Task task){
        if(AdvancedOptions.groupTasksByProject())
        {
            GroupTask.removeTaskFromGroup(task.getProject());
        }
    }
}
