package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.Collection;
import java.util.Set;

/**
 * Created by Hussain on 21/11/2014.
 */
public class GreedyTaskAllocation  extends TaskAllocationMethod{


    @Override
    public void allocateTasks(Graph<Employee, Task> allocationGraph) {
        Set<Employee> employeeNodes = allocationGraph.getEmployeeNodes();
        Set<Task> taskNodes = allocationGraph.getTaskNodes();
        for (Employee employeeNode : employeeNodes) {
            for (Task taskNode : taskNodes) {
                if(hasTaskBeenAssigned(allocationGraph,taskNode))
                {
                    continue;
                }
                boolean skillsMatch = checkSkillsMatch(employeeNode,taskNode);
                if(skillsMatch)
                {
                    allocationGraph.addEdge(employeeNode,taskNode);
                }
            }
        }
    }

    public boolean hasTaskBeenAssigned(Graph<Employee,Task> allocationGraph, Task t){
        Collection<Task> assignedTasks = allocationGraph.getEmployeeToTaskMapping().values();
        for (Task assignedTask : assignedTasks) {
            if(assignedTask.equals(t))
            {
                return  true;
            }
        }
        return false;
    }
}
