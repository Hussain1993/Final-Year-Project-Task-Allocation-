package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.Stack;

/**
 * Created by Hussain on 29/01/2015.
 */
public class Path {
    Graph<Node<Employee>, Node<Task>> copyOfAllocationGraph;

    public Path(Graph<Node<Employee>, Node<Task>> allocationGraph){
        this.copyOfAllocationGraph = allocationGraph;
        this.copyOfAllocationGraph.clear();
    }

    public void addNewPath(Stack<Node> pathStack){
        while (!pathStack.isEmpty())
        {
            if(pathStack.peek().getNodeType().equals(NodeType.SOURCE))
            {
                pathStack.pop();
            }
            else if(pathStack.peek().getNodeType().equals(NodeType.SINK))
            {
                pathStack.pop();
            }
            else
            {
                Node<Task> taskNode = null;
                Node<Employee> employeeNode = null;
                if(pathStack.peek().getNodeType().equals(NodeType.TASK))
                {
                    taskNode = pathStack.pop();
                }
                if(pathStack.peek().getNodeType().equals(NodeType.EMPLOYEE))
                {
                    employeeNode = pathStack.pop();
                }
                if(taskNode != null && employeeNode != null)
                {
                    copyOfAllocationGraph.addEdge(employeeNode,taskNode);
                }
            }
        }
    }

    public Graph<Node<Employee>, Node<Task>> getAllocationGraph(){
        return this.copyOfAllocationGraph;
    }

}
