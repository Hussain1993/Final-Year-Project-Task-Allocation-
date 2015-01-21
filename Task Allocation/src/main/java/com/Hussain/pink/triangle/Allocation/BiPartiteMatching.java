package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.Set;

/**
 * Created by Hussain on 21/01/2015.
 */
public class BiPartiteMatching extends TaskAllocationMethod {

    @Override
    public void allocateTasks(Graph<Employee, Task> allocationGraph) {
        Set<Employee> employeeNodes = allocationGraph.getEmployeeNodes();
        Set<Task> taskNodes = allocationGraph.getTaskNodes();
        //Loop over every single employee and find the task they can be matched to
        //DON't keep a record of which tasks that have been matched
        for(Employee e : employeeNodes)
        {
            for(Task t : taskNodes)
            {
                boolean employeeAvailableForTask = checkEmployeeAvailableForTask(e,t);
                boolean skillsMatch = checkSkillsMatch(e,t);
                if(employeeAvailableForTask && skillsMatch)
                {
                    allocationGraph.addEdge(e,t);
                }
            }
        }
    }
}
