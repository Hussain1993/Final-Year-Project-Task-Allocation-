package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NodeTest {

    @Test
    public void testGetNodeType(){
        Node<Employee> employeeNode = new Node<>(new Employee(1,"",null,0),NodeType.EMPLOYEE);
        assertEquals(NodeType.EMPLOYEE, employeeNode.getNodeType());
    }

    @Test
    public void testGetObject(){
        Node<Employee> employeeNode = new Node<>(new Employee(1,"",null,0),NodeType.EMPLOYEE);
        Node<Task> taskNode = new Node<>(new Task(1,"",1,1L,1L,false,null),NodeType.TASK);
        Node<String> source = new Node<>("Source",NodeType.SOURCE);

        assertTrue(employeeNode.getObject() instanceof Employee);
        assertTrue(taskNode.getObject() instanceof Task);
        assertTrue(source.getObject() instanceof  String);
    }

}