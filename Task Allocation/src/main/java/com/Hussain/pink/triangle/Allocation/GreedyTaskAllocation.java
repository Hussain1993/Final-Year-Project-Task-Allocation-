package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Set;

/**
 * Created by Hussain on 21/11/2014.
 */
public class GreedyTaskAllocation  extends TaskAllocationMethod{
    private Task[] matchedTasks;


    @Override
    public void allocateTasks(Graph<Employee, Task> allocationGraph) {
        Set<Employee> employeeNodes = allocationGraph.getEmployeeNodes();
        Set<Task> taskNodes = allocationGraph.getTaskNodes();
        for (Employee employeeNode : employeeNodes) {
            for (Task taskNode : taskNodes) {
                //Check if this task has been matched up with someone else already
                if(ArrayUtils.contains(matchedTasks,taskNode))
                {
                    continue;
                }
                boolean employeeAvailableForTask = checkEmployeeAvailableForTask(employeeNode,taskNode);
                boolean skillsMatch = checkSkillsMatch(employeeNode,taskNode);
                if(skillsMatch && employeeAvailableForTask)
                {
                    allocationGraph.addEdge(employeeNode,taskNode);
                    matchedTasks = ArrayUtils.add(matchedTasks,taskNode);
                    //Once a employee has been matched up with a task move to the next employee on the list
                    break;
                }
            }
        }
    }
}
