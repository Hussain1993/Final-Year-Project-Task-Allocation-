package com.Hussain.pink.triangle.Model;

import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

public class PathsTest {

    @Test
    public void testPrintPath(){
        final Node[] p1 = {new Node(new Employee(1,"",null,0), NodeType.EMPLOYEE)
        ,new Node(new Task(100,"",100,1L,1L,false,null),NodeType.TASK)};

        final Node[] p2 = {new Node(new Employee(2,"",null,0),NodeType.EMPLOYEE),
        new Node(new Task(102,"",100,1L,1L,false,null),NodeType.TASK)};

        Stack<Node> stack = new Stack<>();
        stack.push(new Node("Source",NodeType.SOURCE));

        stack.push(new Node(new Employee(1,"",null,0), NodeType.EMPLOYEE));
        stack.push(new Node(new Task(100,"",100,1L,1L,false,null),NodeType.TASK));

        stack.push(new Node(new Employee(2,"",null,0),NodeType.EMPLOYEE));
        stack.push(new Node(new Task(102,"",100,1L,1L,false,null),NodeType.TASK));

        stack.push(new Node("Sink",NodeType.SINK));

        Paths p = new Paths();
        p.addNewPaths(stack);
        assertEquals("[[Node: Employee:  Type:EMPLOYEE], [Node: Task:  Type:TASK]]\n" +
                "[[Node: Employee:  Type:EMPLOYEE], [Node: Task:  Type:TASK]]",p.printPath());

    }

}