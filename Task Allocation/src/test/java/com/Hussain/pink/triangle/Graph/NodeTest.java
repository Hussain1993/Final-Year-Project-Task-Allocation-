package com.Hussain.pink.triangle.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NodeTest {

    @Test
    public void testGetElement() {
        Employee testEmployee = new Employee(1,"fName","lName",new HashSet<Skill>(),10);
        Node<Employee> employeeNode = new Node<>(testEmployee);
        assertEquals(testEmployee,employeeNode.getElement());
    }

    @Test
    public void testToString() {
        Employee testEmployee = new Employee(1,"fName","lName",new HashSet<Skill>(),10);
        Node<Employee> employeeNode = new Node<>(testEmployee);
        assertEquals("Employee: fName lName",employeeNode.toString());
    }

    @Test
    public void testEqualsNull() {
        assertFalse(new Node<>(new Employee(1,null,null,null,0)).equals(null));
    }

    @Test
    public void testEqualsSameObject() {
        Node<Employee> employeeNode = new Node<>(new Employee(1,null,null,null,0));
        assertTrue(employeeNode.equals(employeeNode));
    }

    @Test
    public void testEqualsDifferentObject() {
        Node<Employee> employeeNode = new Node<>(new Employee(1,null,null,null,0));
        assertFalse(employeeNode.equals("Test"));
    }

    @Test
    public void testEqualsValid() {
        Node<Employee> employeeNode = new Node<>(new Employee(1,null,null,null,0));
        assertTrue(employeeNode.equals(new Node<>(new Employee(1,null,null,null,0))));
    }

    @Test
    public void testEqualsNotValid() {
        Node<Employee> employeeNode = new Node<>(new Employee(1,null,null,null,0));
        assertFalse(employeeNode.equals(new Node<>(new Employee(2,null,null,null,9))));
    }
}