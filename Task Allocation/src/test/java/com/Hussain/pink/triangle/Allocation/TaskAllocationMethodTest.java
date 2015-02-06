package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.Graph;
import com.Hussain.pink.triangle.Model.Graph.Node;
import com.Hussain.pink.triangle.Model.Graph.NodeType;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import com.mockrunner.mock.jdbc.MockResultSet;
import com.mockrunner.mock.jdbc.MockResultSetMetaData;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Hussain on 18/11/2014.
 */
public class TaskAllocationMethodTest {

    @Test
    public void testBuildGraph() {
        Set<Node<Employee>> employeeNodes = buildEmployeeNodes();
        Set<Node<Task>> taskNodes = buildTaskNodes();

        MockResultSet mockEmployeeResultSet = new MockResultSet("EmployeeResultSet");
        MockResultSetMetaData mockEmployeeResultSetMetaData = new MockResultSetMetaData();
        mockEmployeeResultSetMetaData.setColumnCount(8);
        MockResultSet mockTaskResultSet = new MockResultSet("TaskResultSet");

        mockEmployeeResultSet.setResultSetMetaData(mockEmployeeResultSetMetaData);
        mockEmployeeResultSet.addColumn("ID", new Integer[]{1,2});
        mockEmployeeResultSet.addColumn("Name", new String[]{"Beyonce","Alex"});
        mockEmployeeResultSet.addColumn("Skills", new String[]{"1,2","1,2"});
        mockEmployeeResultSet.addColumn("Cost", new Integer[]{300,200});
        mockEmployeeResultSet.addColumn("Proficiency",new String[]{"1,1","1,1"});

        mockTaskResultSet.addColumn("ID", new Integer[]{3,4});
        mockTaskResultSet.addColumn("NAME", new String[]{"T1","T2"});
        mockTaskResultSet.addColumn("PROJECT ID", new Integer[]{100,102});
        mockTaskResultSet.addColumn("Date From", new Date[]{new Date(1L),
                new Date(1L)});
        mockTaskResultSet.addColumn("Date To", new Date[]{new Date(1L),
                new Date(1L)});
        mockTaskResultSet.addColumn("Completed", new Boolean[]{false,false});
        mockTaskResultSet.addColumn("Skills", new String[]{"1,2","1,2"});
        mockTaskResultSet.addColumn("Proficiency Required", new String[]{"1,1","1,1"});

        TaskAllocationMethod greedyTaskAllocationMethod = new GreedyTaskAllocation();
        Graph<Node<Employee>,Node<Task>> testGraph =
                greedyTaskAllocationMethod.buildGraph(mockEmployeeResultSet, mockTaskResultSet);

        assertTrue("All the employees have not been added",testGraph.getEmployeeNodes().containsAll(employeeNodes));
        assertTrue("All the tasks have not been added", testGraph.getTaskNodes().containsAll(taskNodes));
    }

    @Test
    public void testBuildGraph2(){
        MockResultSet mockEmployeeResultSet = new MockResultSet("EmployeeResultSet");

        MockResultSetMetaData mockEmployeeResultSetMetaData = new MockResultSetMetaData();
        mockEmployeeResultSetMetaData.setColumnCount(9);

        mockEmployeeResultSet.setResultSetMetaData(mockEmployeeResultSetMetaData);

        mockEmployeeResultSet.addColumn("ID",new Integer[]{1});
        mockEmployeeResultSet.addColumn("NAME", new String[] {"TestName"});
        mockEmployeeResultSet.addColumn("SKILLS", new String[] {"1"});
        mockEmployeeResultSet.addColumn("COST", new Integer[] {200});
        mockEmployeeResultSet.addColumn("PROFICIENCY", new String[]{"1"});

        mockEmployeeResultSet.addColumn("TASK_ID",new Integer[] {100});
        mockEmployeeResultSet.addColumn("DATE_FROM", new Date[] {new Date(1L)});
        mockEmployeeResultSet.addColumn("DATE_TO", new Date[] {new Date(1L)});
        mockEmployeeResultSet.addColumn("COMPLETED", new Boolean[] {false});

        TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();
        Graph<Node<Employee>, Node<Task>> testGraph = taskAllocationMethod.buildGraph(mockEmployeeResultSet,null);

        ArrayList<Node<Employee>> employeeNodes = testGraph.getEmployeeNodes();
        assertNotNull(employeeNodes.get(0).getObject().getTaskAssigned());
    }

    private Set<Node<Employee>> buildEmployeeNodes(){
        LinkedHashSet<Skill> skills = new LinkedHashSet<>();
        skills.add(new Skill("1",1));
        skills.add(new Skill("2",1));
        LinkedHashSet<Node<Employee>> employeeNodes = new LinkedHashSet<>();
        employeeNodes.add(new Node<>(new Employee(1,"Beyonce",skills,300), NodeType.EMPLOYEE));
        employeeNodes.add(new Node<>(new Employee(2,"Alex",skills,200),NodeType.EMPLOYEE));
        return employeeNodes;
    }

    private Set<Node<Task>> buildTaskNodes(){
        LinkedHashSet<Skill> skills = new LinkedHashSet<>();
        skills.add(new Skill("1",1));
        skills.add(new Skill("2",1));
        LinkedHashSet<Node<Task>> taskNodes = new LinkedHashSet<>();
        taskNodes.add(new Node<>(new Task(3,"T1",100,1L,1L,false,skills),NodeType.TASK));
        taskNodes.add(new Node<>(new Task(4,"T2",102,1L,1L,false,skills),NodeType.TASK));
        return taskNodes;
    }

    @Test
    public void testCheckSkillsMatchValid(){
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Skill java = new Skill("java",1);
        LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();
        skillSet.add(java);
        Employee e = new Employee(1,"Test",skillSet,0);
        Task t = new Task(1,"t1",1,1,1,false,skillSet);
        assertTrue(taskAllocation.checkSkillsMatch(e,t));
    }

    @Test
    public void testCheckSkillsMatchInValid(){
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Skill uml = new Skill("UML",1);
        Skill java = new Skill("java",1);
        LinkedHashSet<Skill> employeeSkillSet = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkillSet = new LinkedHashSet<>();
        employeeSkillSet.add(uml);
        taskSkillSet.add(java);

        Employee e = new Employee(1, "", employeeSkillSet, 0);
        Task t = new Task(1, "", 1, 1, 1, false, taskSkillSet);

        assertFalse(taskAllocation.checkSkillsMatch(e,t));
    }

    @Test
    public void testCheckSkillsMatchOrder() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Skill uml = new Skill("UML",2);
        Skill java = new Skill("Java",1);

        LinkedHashSet<Skill> employeeSkillSet = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkillSet = new LinkedHashSet<>();

        employeeSkillSet.add(uml);
        employeeSkillSet.add(java);

        taskSkillSet.add(uml);
        taskSkillSet.add(java);

        Employee e = new Employee(1,"",employeeSkillSet,0);
        Task t = new Task(1,"",1,1,1,false,taskSkillSet);

        assertTrue(taskAllocation.checkSkillsMatch(e,t));

    }

    @Test
    public void testCheckSkillsMatchEmployeeHasMoreSkills() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();

        Skill java = new Skill("Java",1);
        Skill uml = new Skill("UML",1);
        Skill windowsPhone = new Skill("Windows Phone",1);

        LinkedHashSet<Skill> employeeSkills = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkills = new LinkedHashSet<>();

        employeeSkills.add(java);
        employeeSkills.add(uml);
        employeeSkills.add(windowsPhone);

        taskSkills.add(java);
        taskSkills.add(uml);

        Employee e = new Employee(1,null,employeeSkills,0);
        Task t = new Task(1, null, 1, 1, 1, false, taskSkills);

        assertTrue(taskAllocation.checkSkillsMatch(e,t));
    }

    @Test
    public void testCheckSkillsMatchEmployeeIsMoreSkilled() {
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();

        Skill eSkill1 = new Skill("Java",2);
        Skill uml = new Skill("UML", 1);
        Skill windowsPhone = new Skill("Windows Phone",1);

        Skill taskJavaSkill = new Skill("Java",1);

        LinkedHashSet<Skill> employeeSkills = new LinkedHashSet<>();
        LinkedHashSet<Skill> taskSkills = new LinkedHashSet<>();

        employeeSkills.add(eSkill1);
        employeeSkills.add(uml);
        employeeSkills.add(windowsPhone);

        taskSkills.add(taskJavaSkill);
        taskSkills.add(uml);

        Employee e = new Employee(1, null, employeeSkills, 0);
        Task t = new Task(1, null, 1, 1, 1, false, taskSkills);

        assertTrue(taskAllocation.checkSkillsMatch(e,t));

    }

    @Test
    public void testBuildSkillSet() {
        GreedyTaskAllocation taskAllocation  = new GreedyTaskAllocation();
        String skills = "Java,UML";
        String proficiency = "1,2";

        Skill java = new Skill("Java",1);
        Skill uml = new Skill("UML",2);

        LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();
        skillSet.add(java);
        skillSet.add(uml);

        Skill[] skillArray = skillSet.toArray(new Skill[skillSet.size()]);

        LinkedHashSet<Skill> skillLinkedHashSet = taskAllocation.buildSkillSet(skills, proficiency);
        assertArrayEquals(skillArray, skillLinkedHashSet.toArray(new Skill[skillLinkedHashSet.size()]));

    }

    @Test
    public void testCheckEmployeeAvailableForTask_TimeRangeDoesOverlap() {
        Task task = new Task(1,null,100,1073779200000L,1100131200000L,false,null);
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Employee e = new Employee(1,null,null,0,task);

        Task t = new Task(1,null,1,1089500400000L,1089500400000L,false,null);

        assertFalse(taskAllocation.checkEmployeeAvailableForTask(e, t));
    }

    @Test
    public void testCheckEmployeeAvailableForTask_TimeRangeDoesNotOverlap() {
        Task task = new Task(1,null,100,1073779200000L,1100131200000L,false,null);
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Employee e = new Employee(1,null,null,0,task);

        Task t = new Task(1,null,1,1105401600000L,1108080000000L,false,null);

        assertTrue(taskAllocation.checkEmployeeAvailableForTask(e,t));

    }

    @Test
    public void testCheckEmployeeAvailableForTask_TaskCompleted(){
        Task taskAssignedToEmployee = new Task(100,"Test",900,1L,1L,true,null);
        Employee employee = new Employee(1,"Test",null,0,taskAssignedToEmployee);

        TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();

        assertTrue(taskAllocationMethod.checkEmployeeAvailableForTask(employee,null));
    }

    @Test
    public void testTaskGroupOrder(){
        TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();

        String expectedProjectGroupOrder = "SELECT TASKS.ID, TASKS.NAME, TASKS.PROJECT_ID, TASKS.DATE_FROM, " +
                "TASKS.DATE_TO, TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), " +
                "GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED) FROM TASKS, SKILLS, TASK_SKILLS, " +
                "PROJECTS  WHERE TASKS.ID = TASK_SKILLS.TASK_ID AND SKILLS.ID = TASK_SKILLS.SKILL_ID " +
                "AND TASKS.PROJECT_ID = PROJECTS.ID group by projects.id, tasks.id";

        String expectedTaskGroupOrder = "SELECT TASKS.ID, TASKS.NAME, TASKS.PROJECT_ID, TASKS.DATE_FROM, " +
                "TASKS.DATE_TO, TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), " +
                "GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED) FROM TASKS, SKILLS, TASK_SKILLS, " +
                "PROJECTS  WHERE TASKS.ID = TASK_SKILLS.TASK_ID AND SKILLS.ID = TASK_SKILLS.SKILL_ID AND " +
                "TASKS.PROJECT_ID = PROJECTS.ID group by tasks.id";

        taskAllocationMethod.setTaskGroupOrder(true);

        assertEquals(expectedProjectGroupOrder,taskAllocationMethod.getTaskQuery().toString());

        taskAllocationMethod = new GreedyTaskAllocation();

        taskAllocationMethod.setTaskGroupOrder(false);

        assertEquals(expectedTaskGroupOrder,taskAllocationMethod.getTaskQuery().toString());
    }

    @Test
    public void testQueryOrder(){
        TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();

        taskAllocationMethod.setEmployeeQuery(false);

        taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_ALPHABETICAL,TaskAllocationMethod.EMPLOYEE_QUERY);
        taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_ALPHABETICAL,TaskAllocationMethod.TASK_QUERY);

        String expectedEmployeeQuery = "select employees.id,concat_ws(' ',employees.first_name,employees.last_name) " +
                "as name,group_concat(skills.skill),\n" +
                " employees.cost, group_concat(employee_skills.PROFICIENCY)\n" +
                " from employee_skills join employees on employee_skills.employee_id = employees.id\n" +
                " join skills on employee_skills.skill_id = skills.id group by employees.id order by name asc";

        String expectedTaskQuery = "SELECT TASKS.ID, TASKS.NAME, TASKS.PROJECT_ID, TASKS.DATE_FROM, TASKS.DATE_TO, " +
                "TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED) " +
                "FROM TASKS, SKILLS, TASK_SKILLS, PROJECTS  WHERE TASKS.ID = TASK_SKILLS.TASK_ID AND " +
                "SKILLS.ID = TASK_SKILLS.SKILL_ID AND TASKS.PROJECT_ID = PROJECTS.ID order by name asc";

        assertEquals(expectedEmployeeQuery,taskAllocationMethod.getEmployeeQuery().toString());
        assertEquals(expectedTaskQuery, taskAllocationMethod.getTaskQuery().toString());
    }

    @Test
    public void testSetEmployeeQuery(){
       TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();

        taskAllocationMethod.setEmployeeQuery(true);

        String expectedEmployeeQuery = "SELECT EMPLOYEES.ID, CONCAT_WS(' ',EMPLOYEES.FIRST_NAME,EMPLOYEES.LAST_NAME) " +
                "AS NAME, GROUP_CONCAT(SKILLS.SKILL), EMPLOYEES.COST, GROUP_CONCAT(EMPLOYEE_SKILLS.PROFICIENCY), " +
                "ASSIGNED_TO.TASK_ID, TASKS.DATE_FROM, TASKS.DATE_TO, TASKS.COMPLETED  FROM EMPLOYEE_SKILLS " +
                "JOIN EMPLOYEES ON EMPLOYEE_SKILLS.EMPLOYEE_ID = EMPLOYEES.ID JOIN SKILLS ON " +
                "EMPLOYEE_SKILLS.SKILL_ID = SKILLS.ID LEFT JOIN ASSIGNED_TO ON EMPLOYEES.ID = ASSIGNED_TO.EMPLOYEE_ID " +
                "LEFT JOIN TASKS ON ASSIGNED_TO.TASK_ID = TASKS.ID GROUP BY EMPLOYEES.ID";

        assertEquals(expectedEmployeeQuery, taskAllocationMethod.getEmployeeQuery().toString());
    }
}
