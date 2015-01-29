package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Hussain on 29/01/2015.
 */
public class Paths {
    ArrayList<Node[]> paths;

    public Paths(){
        paths = new ArrayList<>();
    }

    public ArrayList<Node[]> getPaths(){
        return this.paths;
    }

    public void addNewPaths(Stack<Node> pathStack){
        while (!pathStack.isEmpty())
        {
            if(pathStack.peek().getNodeType().equals(NodeType.SINK))
            {
                pathStack.pop();
            }
            else if(pathStack.peek().getNodeType().equals(NodeType.SOURCE))
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
                    Node[] path = {employeeNode,taskNode};
                    paths.add(path);
                }
            }
        }
    }

    public String printPath(){
        return toString();
    }

    public String toString(){
        String path = "";
        for(Node [] pathNodes : paths)
        {
            path = path + Arrays.toString(pathNodes) + "\n";
        }
        return path.trim();
    }
}
