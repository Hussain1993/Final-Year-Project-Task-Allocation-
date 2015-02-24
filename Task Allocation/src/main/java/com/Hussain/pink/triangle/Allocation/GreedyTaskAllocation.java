package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Exception.ProjectGroupNotFoundException;
import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.GroupTask;
import com.Hussain.pink.triangle.Organisation.Task;
import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A specific task allocation scheme, in this
 * instance it is a greedy way of allocating the tasks the employees,
 * where this will assign the employee to the first task that they have
 * the right skills for and this task cannot be further assigned to anyone else
 *
 * Created by Hussain on 21/11/2014.
 */
public class GreedyTaskAllocation  extends TaskAllocationMethod{
    private Task[] matchedTasks; //A list of matched tasks


    @Override
    public void allocateTasks(Graph<Node<Employee>, Node<Task>> allocationGraph) {
        ArrayList<Node<Employee>> employeeNodes = allocationGraph.getEmployeeNodes();
        ArrayList<Node<Task>> taskNodes = allocationGraph.getTaskNodes();

        if(AdvancedOptions.getUseHeuristic())//They would like to use the heuristic function when matching employees to tasks
        {
            greedyHeuristic(employeeNodes,taskNodes,allocationGraph);
        }
        else
        {
            greedy(employeeNodes,taskNodes,allocationGraph);
        }
    }


    private void greedy(List<Node<Employee>> employeeNodes, List<Node<Task>> taskNodes,
                        Graph<Node<Employee>, Node<Task>> allocationGraph){
        for (Node<Employee> employeeNode : employeeNodes) {
            Employee employee = employeeNode.getObject();
            for (Node<Task> taskNode : taskNodes) {
                Task task = taskNode.getObject();
                //Check if this task has been matched up with someone else already
                if(ArrayUtils.contains(matchedTasks,task))
                {
                    continue;
                }
                boolean employeeAvailableForTask = checkEmployeeAvailableForTask(employee,task);
                boolean skillsMatch = checkSkillsMatch(employee,task);
                if(skillsMatch && employeeAvailableForTask)
                {
                    allocationGraph.addEdge(employeeNode,taskNode);
                    matchedTasks = ArrayUtils.add(matchedTasks, task);
                    processProjectGroup(task);
                    //Once a employee has been matched up with a task move to the next employee on the list
                    break;
                }
            }
        }
    }

    public void greedyHeuristic(List<Node<Employee>> employeeNodes, List<Node<Task>> taskNodes,
                                 Graph<Node<Employee>, Node<Task>> allocationGraph){
        addAllPossibleMatching(employeeNodes,taskNodes,allocationGraph);
        for(Node<Task> taskNode : taskNodes)
        {
            ArrayList<Node<Employee>> listOfApplicableEmployees = allocationGraph.getMappedEmployees(taskNode);
            if(listOfApplicableEmployees.size() > 1)
            {
                Node<Employee> bestMatchedEmployee = EuclideanHeuristic.findBestMatchEmployee(listOfApplicableEmployees);
                listOfApplicableEmployees.remove(bestMatchedEmployee);
                allocationGraph.removeEdgesForHeuristicFunction(listOfApplicableEmployees,bestMatchedEmployee,taskNode);
                allocationGraph.addEdge(bestMatchedEmployee,taskNode);
                processProjectGroup(taskNode.getObject());
            }
        }
    }

    private void processProjectGroup(Task task){
        if(AdvancedOptions.groupTasksByProject())
        {
            try{
                GroupTask.removeTaskFromGroup(task.getProject());
            }
            catch (ProjectGroupNotFoundException e){
                LOG.error("There was an error when removing the task from the project",e);
            }
        }
    }
}
