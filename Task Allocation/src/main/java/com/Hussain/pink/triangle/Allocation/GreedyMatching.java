package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.GroupTask;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Hussain on 05/03/2015.
 */
public class GreedyMatching {

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

    private static void processGroupTask(Task task){
        if(AdvancedOptions.groupTasksByProject())
        {
            GroupTask.removeTaskFromGroup(task.getProject());
        }
    }
}
