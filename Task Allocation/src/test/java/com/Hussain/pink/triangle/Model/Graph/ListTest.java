package com.Hussain.pink.triangle.Model.Graph;

import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ListTest {
    private static final Node<String> SOURCE = new Node<>("Source",NodeType.SOURCE);
    private static final Node<String> SINK = new Node<>("Sink",NodeType.SINK);

    @Test
    public void testBuildList() {
        Node<Employee> e1 = new Node<>(new Employee(1,"1",null,0), NodeType.EMPLOYEE);
        Node<Employee> e2 = new Node<>(new Employee(2,"2",null,0), NodeType.EMPLOYEE);
        Node<Employee> e3 = new Node<>(new Employee(3,"3",null,0), NodeType.EMPLOYEE);

        Node<Task> t4 = new Node<>(new Task(4,"4",1,1L,1L,false,null),NodeType.TASK);
        Node<Task> t5 = new Node<>(new Task(5,"5",1,1L,1L,false,null),NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);
        testGraph.addEmployeeNode(e3);

        testGraph.addTaskNode(t4);
        testGraph.addTaskNode(t5);

        testGraph.addEdge(e1, t4);
        testGraph.addEdge(e2,t4);
        testGraph.addEdge(e2,t5);
        testGraph.addEdge(e3,t5);

        AdjacencyList testList = new AdjacencyList(testGraph);

        assertTrue("The source is not pointing to all three employees",testList.listElements(SOURCE).size() == 3);
        assertTrue("Employee 1 is not pointing to 1 task", testList.listElements(e1).size() == 1);
        assertTrue("Employee 2 is not pointing to 2 tasks", testList.listElements(e2).size() == 2);
        assertTrue("Employee 3 is not pointing to 1 tasks", testList.listElements(e3).size() == 1);

        assertTrue("Task 4 has not been matched with two employees", testList.listElements(t4).size() == 3);
        assertTrue("Task 5 has not been matched with two employees", testList.listElements(t5).size() == 3);

    }

    @Test
    public void testGetElements() {
        Node<Employee> e1 = new Node<>(new Employee(1,"1",null,0), NodeType.EMPLOYEE);
        Node<Employee> e2 = new Node<>(new Employee(2,"2",null,0), NodeType.EMPLOYEE);
        Node<Employee> e3 = new Node<>(new Employee(3,"3",null,0), NodeType.EMPLOYEE);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();
        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);
        testGraph.addEmployeeNode(e3);

        AdjacencyList testList = new AdjacencyList(testGraph);

        assertTrue(testList.listElements(SOURCE).size() == 3);
    }

    @Test
    public void testRemoveFromList() {
        Node<Employee> e1 = new Node<>(new Employee(1,"1",null,0), NodeType.EMPLOYEE);
        Node<Employee> e2 = new Node<>(new Employee(2,"2",null,0), NodeType.EMPLOYEE);
        Node<Employee> e3 = new Node<>(new Employee(3,"3",null,0), NodeType.EMPLOYEE);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();
        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);
        testGraph.addEmployeeNode(e3);

        AdjacencyList testList = new AdjacencyList(testGraph);

        assertTrue(testList.listElements(SOURCE).size() == 3);

        testList.removeFromList(SOURCE,e1);//Remove e1 from the list

        assertTrue(testList.listElements(SOURCE).size() == 2);

    }

    @Test
    public void testGetOtherEmployeesMappedToSameTask() {
        Node<Employee> e1 = new Node<>(new Employee(1,"",null,0),NodeType.EMPLOYEE);
        Node<Employee> e2 = new Node<>(new Employee(2,"",null,0),NodeType.EMPLOYEE);

        Node<Task> t1 = new Node<>(new Task(1,"",1,1L,1L,false,null),NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addEmployeeNode(e2);

        testGraph.addTaskNode(t1);

        //Make the two employees point to the same task
        testGraph.addEdge(e1,t1);
        testGraph.addEdge(e2,t1);

        AdjacencyList testList = new AdjacencyList(testGraph);

        assertEquals(e2, testList.getOtherEmployeesMappedToSameTask(t1,e1));
    }

    @Test
    public void testGetOtherOtherEmployeesMappedToSameTask_Sink() {
        Node<Employee> e1 = new Node<>(new Employee(1,"",null,0),NodeType.EMPLOYEE);

        Node<Task> t1 = new Node<>(new Task(1,"",1,1L,1L,false,null),NodeType.TASK);

        Graph<Node<Employee>, Node<Task>> testGraph = new Graph<>();

        testGraph.addEmployeeNode(e1);
        testGraph.addTaskNode(t1);
        testGraph.addEdge(e1,t1);

        AdjacencyList testList = new AdjacencyList(testGraph);

        assertEquals(SINK, testList.getOtherEmployeesMappedToSameTask(t1,e1));
    }
}