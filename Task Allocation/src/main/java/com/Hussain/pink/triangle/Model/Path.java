package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Hussain on 29/01/2015.
 */
public class Path {
    private ArrayList<Node[]> paths;

    public Path(){
        paths = new ArrayList<>();
    }

    public void addNewPath(Stack<Node> pathStack){
        while(!pathStack.isEmpty())
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
                if(pathStack.peek().getNodeType().equals(NodeType.TASK))//We know for sure that the the node is a task node
                {
                    taskNode = pathStack.pop();
                }
                if(pathStack.peek().getNodeType().equals(NodeType.EMPLOYEE))//We know for sure that the node is a employee node
                {
                    employeeNode = pathStack.pop();
                }
                if(taskNode != null && employeeNode != null)//Sanity check
                {
                    Node[] path = {employeeNode,taskNode};
                    paths.add(path);
                }
            }
        }
    }

    public void buildPathToMap(Graph<Node<Employee>,Node<Task>> graph){
        if(graph != null)
        {
            graph.clear();
            for(Node [] pathNodes : paths)
            {
                if(pathNodes.length == 2)
                {
                    graph.addEdge(pathNodes[0],pathNodes[1]);
                }
            }
        }
    }


}
