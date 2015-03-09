package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.GroupTask;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * This greedy matching algorithm is
 * used by both the Greedy Task allocation method and
 * the maximum bipartite matching algorithm as well
 *
 * This class will match the employee to the
 * first task that is available to them
 *
 * Created by Hussain on 05/03/2015.
 */
public class GreedyMatching {

    /**
     * This method will match the employee with the first
     * task that is available to them
     * @param unmatchedEmployees The set of all the unmatched employees
     * @param unmatchedTasks The set of all the unmatched tasks
     * @param employeeNodes All the employee nodes we can loop over
     * @param biPartiteGraph This is only here to get the employee object by their name
     * @return A greedy matching
     */
    public static Matching<String> greedy(Set<String> unmatchedEmployees, Set<String> unmatchedTasks, Set<String> employeeNodes, BiPartiteGraph biPartiteGraph){
        Matching<String> matching = new Matching<>();
        LinkedHashSet<String> taskNodesWeHaveAssigned = new LinkedHashSet<>();
        for(String employeeName : employeeNodes)
        {
            for(String taskName : biPartiteGraph.getAdjacentNodes(employeeName))
            {
                if(!taskNodesWeHaveAssigned.contains(taskName))
                {
                    taskNodesWeHaveAssigned.add(taskName);
                    unmatchedEmployees.remove(employeeName);
                    unmatchedTasks.remove(taskName);
                    matching.addMatching(employeeName,taskName);
                    processGroupTask(biPartiteGraph.getTaskByName(taskName));
                    break;
                }
            }
        }
        return matching;
    }

    /**
     * This is a helper method to deal with
     * when the user decides to group the
     * tasks by their project
     * @param task The task to remove from the project
     */
    public static void processGroupTask(Task task){
        if(AdvancedOptions.groupTasksByProject())
        {
            GroupTask.removeTaskFromGroup(task.getProject());
        }
    }
}
