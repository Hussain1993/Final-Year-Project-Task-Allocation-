package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class PathTest {

    @Test
    public void testPath(){
        Graph<Node<Employee>, Node<Task>> expectedGraph = buildExpectedGraph();
        Graph<Node<Employee>, Node<Task>> testGraph = buildExpectedGraph();
        testGraph.clear();

        Stack<Node> stack = new Stack<>();

        stack.push(new Node("Source",NodeType.SOURCE));

        stack.push(new Node<>(new Employee(1,"E1",null,0), NodeType.EMPLOYEE));
        stack.push(new Node<>(new Task(7,"T7",100,1L,1L,false,null),NodeType.TASK));

        stack.push(new Node<>(new Employee(3,"E3",null,0),NodeType.EMPLOYEE));
        stack.push(new Node<>(new Task(9,"T9",100,1L,1L,false,null),NodeType.TASK));

        stack.push(new Node("Sink",NodeType.SINK));

        Path path = new Path(testGraph);
        path.addNewPath(stack);

        assertEquals(path.getAllocationGraph(),expectedGraph);

    }

    private Graph<Node<Employee>, Node<Task>> buildExpectedGraph(){
        Node<Employee> e1 = new Node<>(new Employee(1,"E1",null,0), NodeType.EMPLOYEE);
        Node<Task> t7 = new Node<>(new Task(7,"T7",100,1L,1L,false,null),NodeType.TASK);

        Node<Employee> e3 = new Node<>(new Employee(3,"E3",null,0),NodeType.EMPLOYEE);
        Node<Task> t9 = new Node<>(new Task(9,"T9",100,1L,1L,false,null),NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> expectedGraph = new Graph<>();

        expectedGraph.addEdge(e1,t7);
        expectedGraph.addEdge(e3,t9);
        return expectedGraph;
    }

}