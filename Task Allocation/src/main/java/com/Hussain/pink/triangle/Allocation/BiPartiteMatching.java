package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.List;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.LinkedHashSet;
import java.util.Stack;

/**
 * Maximum BiPartite matching of the tasks and employees
 *
 * Created by Hussain on 21/01/2015.
 */
public class BiPartiteMatching extends TaskAllocationMethod {
    private static final Node<String> SOURCE = new Node<>("Source", NodeType.SOURCE);
    private static final Node<String> SINK = new Node<>("Sink",NodeType.SINK);
    private static final Stack<Node> stack = new Stack<>();
    private static final LinkedHashSet<Node> nodesWeHaveVisited = new LinkedHashSet<>();

    @Override
    public void allocateTasks(Graph<Node<Employee>, Node<Task>> allocationGraph) {
        java.util.List<Node<Employee>> employeeNodes = allocationGraph.getEmployeeNodes();
        java.util.List<Node<Task>> taskNodes = allocationGraph.getTaskNodes();
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

    public void biPartiteMatching(Graph<Node<Employee>, Node<Task>> allocationGraph){
        List adjacencyList = new List(allocationGraph);
        stack.push(SOURCE);
        while (!stack.isEmpty())
        {
            Node top = stack.peek();
            while(!adjacencyList.listElements(top).isEmpty())
            {
                Node first = adjacencyList.getFirstElement(top);
                if(!nodesWeHaveVisited.contains(first))
                {
                    stack.push(first);
                    adjacencyList.removeFromList(top,first);
                    if(stack.peek() != SINK)
                    {
                        nodesWeHaveVisited.add(stack.peek());
                    }
                    else
                    {
                        //Empty the stack and the print the path
                        stack.clear();
                        stack.push(SOURCE);
                    }
                }
                stack.pop();
            }
        }
    }
}
