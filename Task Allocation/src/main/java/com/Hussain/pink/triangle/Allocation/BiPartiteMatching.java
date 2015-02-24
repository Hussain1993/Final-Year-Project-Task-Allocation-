package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.AdjacencyList;
import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Model.Path;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Maximum BiPartite matching of the tasks and employees
 *
 * Created by Hussain on 21/01/2015.
 */
public class BiPartiteMatching extends TaskAllocationMethod {
    private static final Node<String> SOURCE = new Node<>("Source", NodeType.SOURCE);
    private static final Node<String> SINK = new Node<>("Sink",NodeType.SINK);

    private Path path = new Path();

    @Override
    public void allocateTasks(Graph<Node<Employee>, Node<Task>> allocationGraph) {
        ArrayList<Node<Employee>> employeeNodes = allocationGraph.getEmployeeNodes();
        ArrayList<Node<Task>> taskNodes = allocationGraph.getTaskNodes();

        addAllPossibleMatching(employeeNodes, taskNodes, allocationGraph);

        biPartiteMatching(allocationGraph);
    }

    public void biPartiteMatching(Graph<Node<Employee>, Node<Task>> allocationGraph){
        Stack<Node> stack = new Stack<>();
        ArrayList<Node> nodesWeHaveVisited = new ArrayList<>();
        AdjacencyList adjacencyList = new AdjacencyList(allocationGraph);
        stack.push(SOURCE);
        while (!stack.isEmpty() && !adjacencyList.listElements(stack.peek()).isEmpty())
        {
            while(!adjacencyList.listElements(stack.peek()).isEmpty())
            {
                Node first = adjacencyList.getFirstElement(stack.peek());
                if(!nodesWeHaveVisited.contains(first))
                {
                    adjacencyList.removeFromList(stack.peek(),first);
                    stack.push(first);
                    if(!stack.peek().getNodeType().equals(NodeType.SINK))
                    {
                        nodesWeHaveVisited.add(stack.peek());
                        if(stack.size() == 5)
                        {
                            stack.push(SINK);
                            addPath(stack);
                            stack.clear();
                            stack.push(SOURCE);
                        }
                        continue;
                    }
                    else
                    {
                        addPath(stack);
                        stack.clear();
                        stack.push(SOURCE);
                        continue;
                    }
                }
                adjacencyList.removeFromList(stack.peek(),first);
            }
            stack.pop();
        }
        path.buildPathToMap(allocationGraph);
    }

    private void addPath(Stack<Node> pathStack){
        path.addNewPath(pathStack);
    }

}
