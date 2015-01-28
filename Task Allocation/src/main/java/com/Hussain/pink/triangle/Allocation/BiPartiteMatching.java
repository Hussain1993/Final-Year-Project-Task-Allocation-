package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

/**
 * Maximum BiPartite matching of the tasks and employees
 *
 * Created by Hussain on 21/01/2015.
 */
public class BiPartiteMatching extends TaskAllocationMethod {

    @Override
    public void allocateTasks(Graph<Node<Employee>, Node<Task>> allocationGraph) {
        Set<Node<Employee>> employeeNodes = allocationGraph.getEmployeeNodes();
        Set<Node<Task>> taskNodes = allocationGraph.getTaskNodes();
        //Loop over every single employee and find the task they can be matched to
        //DON't keep a record of which tasks that have been matched
        for(Node<Employee> employeeNode : employeeNodes)
        {
            Employee e = employeeNode.getObject();
            for(Node<Task> taskNode : taskNodes)
            {
                Task t = taskNode.getObject();
                boolean employeeAvailableForTask = checkEmployeeAvailableForTask(e,t);
                boolean skillsMatch = checkSkillsMatch(e,t);
                if(employeeAvailableForTask && skillsMatch)
                {
                    allocationGraph.addEdge(employeeNode,taskNode);
                }
            }
        }
    }

    public void matching(Graph<Employee, Task> allocationGraph){
        ArrayList<LinkedList<Integer>> vertices = new ArrayList<>();

        Set<Employee> employeeNodes = allocationGraph.getEmployeeNodes();
        Integer source = Integer.MAX_VALUE;
        LinkedList<Integer> sourceNodes = new LinkedList<>();
        sourceNodes.add(source);
        for(Employee employee : employeeNodes)
        {
            sourceNodes.add(employee.getId());

        }
        vertices.add(sourceNodes);
    }
}
