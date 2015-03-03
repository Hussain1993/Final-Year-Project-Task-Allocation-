package com.Hussain.pink.triangle.Allocation;

import com.Hussain.pink.triangle.Model.Graph.BiPartiteGraph;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Skill;
import com.Hussain.pink.triangle.Organisation.Task;
import com.mockrunner.mock.jdbc.MockResultSet;
import com.mockrunner.mock.jdbc.MockResultSetMetaData;
import org.junit.Test;

import java.sql.Date;
import java.util.LinkedHashSet;

import static org.junit.Assert.*;

/**
 * Created by Hussain on 18/11/2014.
 */
public class TaskAllocationMethodTest {


    @Test
    public void testBuildGraph() {
        MockResultSet employeeResultSet = buildEmployeeResults();
        MockResultSet taskResultSet = buildTaskResults();
        BiPartiteGraph biPartiteGraph = buildExpectedGraph();

        TaskAllocationMethod greedy = new GreedyTaskAllocation();
        BiPartiteGraph actualBiPartiteGraph = greedy.buildGraph(employeeResultSet,taskResultSet);

        assertTrue(biPartiteGraph.equals(actualBiPartiteGraph));
    }

    private MockResultSet buildEmployeeResults(){
        MockResultSet mockEmployeeResultSet = new MockResultSet("EmployeeResultSet");
        MockResultSetMetaData mockEmployeeMockResultSetMetaData = new MockResultSetMetaData();
        mockEmployeeMockResultSetMetaData.setColumnCount(8);
        mockEmployeeResultSet.setResultSetMetaData(mockEmployeeMockResultSetMetaData);
        mockEmployeeResultSet.addColumn("ID",new Integer[]{1,2});
        mockEmployeeResultSet.addColumn("NAME",new String [] {"E1","E2"});
        mockEmployeeResultSet.addColumn("SKILLS",new String [] {"Java,C++","Java"});
        mockEmployeeResultSet.addColumn("COST",new Integer [] {0,0});
        mockEmployeeResultSet.addColumn("PROFICIENCY",new String [] {"1,1","1"});
        return mockEmployeeResultSet;
    }

    private MockResultSet buildTaskResults(){
        MockResultSet mockTaskResultSet = new MockResultSet("TasksResultSet");
        mockTaskResultSet.addColumn("ID",new Integer [] {1,2});
        mockTaskResultSet.addColumn("NAME",new String [] {"T1","T2"});
        mockTaskResultSet.addColumn("PROJECT_ID",new Integer [] {0,0});
        mockTaskResultSet.addColumn("DATE_FROM",new Date [] {new Date(1L), new Date(1L)});
        mockTaskResultSet.addColumn("DATE_TO",new Date [] {new Date(1L), new Date(1L)});
        mockTaskResultSet.addColumn("COMPLETED",new Boolean [] {false,false});
        mockTaskResultSet.addColumn("SKILLS",new String [] {"Java","C++"});
        mockTaskResultSet.addColumn("PROFICIENCY_REQUIRED",new String [] {"1","1"});
        mockTaskResultSet.addColumn("PROJECT",new String [] {"Stock App","Hardware"});
        return mockTaskResultSet;
    }

    private BiPartiteGraph buildExpectedGraph(){
        Employee e1 = new Employee(1, "E1", null, 0);
        Employee e2 = new Employee(2, "E2", null, 0);

        Task t1 = new Task(1, "T1", null, 1L, 1L, false, null);
        Task t2 = new Task(2, "T2", null, 1L, 1L, false, null);

        BiPartiteGraph biPartiteGraph = new BiPartiteGraph();
        biPartiteGraph.addEmployeeToIndexMap(e1);
        biPartiteGraph.addEmployeeToIndexMap(e2);
        biPartiteGraph.addTaskToIndexMap(t1);
        biPartiteGraph.addTaskToIndexMap(t2);

        biPartiteGraph.addEdge(e1,t1);
        biPartiteGraph.addEdge(e1,t2);
        biPartiteGraph.addEdge(e2,t1);
        return biPartiteGraph;
    }

    @Test
    public void testCheckSkillsMatchValid(){
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Skill java = new Skill("java",1);
        LinkedHashSet<Skill> skillSet = new LinkedHashSet<>();
        skillSet.add(java);
        Employee e = new Employee(1,"Test",skillSet,0);
        Task t = new Task(1,"t1",null,1,1,false,skillSet);
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
        Task t = new Task(1, "", null, 1, 1, false, taskSkillSet);

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
        Task t = new Task(1,"",null,1,1,false,taskSkillSet);

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
        Task t = new Task(1, null, null, 1, 1, false, taskSkills);

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
        Task t = new Task(1, null, null, 1, 1, false, taskSkills);

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
        Task task = new Task(1,null,null,1073779200000L,1100131200000L,false,null);
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Employee e = new Employee(1,null,null,0,task);

        Task t = new Task(1,null,null,1089500400000L,1089500400000L,false,null);

        assertFalse(taskAllocation.checkEmployeeAvailableForTask(e, t));
    }

    @Test
    public void testCheckEmployeeAvailableForTask_TimeRangeDoesNotOverlap() {
        Task task = new Task(1,null,null,1073779200000L,1100131200000L,false,null);
        GreedyTaskAllocation taskAllocation = new GreedyTaskAllocation();
        Employee e = new Employee(1,null,null,0,task);

        Task t = new Task(1,null,null,1105401600000L,1108080000000L,false,null);

        assertTrue(taskAllocation.checkEmployeeAvailableForTask(e,t));

    }

    @Test
    public void testCheckEmployeeAvailableForTask_TaskCompleted(){
        Task taskAssignedToEmployee = new Task(100,"Test",null,1L,1L,true,null);
        Employee employee = new Employee(1,"Test",null,0,taskAssignedToEmployee);

        TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();

        assertTrue(taskAllocationMethod.checkEmployeeAvailableForTask(employee,null));
    }

    @Test
    public void testTaskGroupOrder(){
        TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();

        String expectedProjectGroupOrder = "SELECT TASKS.ID, TASKS.NAME, TASKS.PROJECT_ID, TASKS.DATE_FROM, " +
                "TASKS.DATE_TO, TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), " +
                "GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED), PROJECTS.NAME FROM TASKS, SKILLS, TASK_SKILLS, " +
                "PROJECTS  WHERE TASKS.ID = TASK_SKILLS.TASK_ID AND SKILLS.ID = TASK_SKILLS.SKILL_ID " +
                "AND TASKS.PROJECT_ID = PROJECTS.ID group by projects.id, tasks.id";

        String expectedTaskGroupOrder = "SELECT TASKS.ID, TASKS.NAME, TASKS.PROJECT_ID, TASKS.DATE_FROM, " +
                "TASKS.DATE_TO, TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), " +
                "GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED), PROJECTS.NAME FROM TASKS, SKILLS, TASK_SKILLS, " +
                "PROJECTS  WHERE TASKS.ID = TASK_SKILLS.TASK_ID AND SKILLS.ID = TASK_SKILLS.SKILL_ID AND " +
                "TASKS.PROJECT_ID = PROJECTS.ID group by tasks.id";

        taskAllocationMethod.setTaskGroupOrder(true);

        assertEquals(expectedProjectGroupOrder,taskAllocationMethod.getTaskQuery().toString().trim());

        taskAllocationMethod = new GreedyTaskAllocation();

        taskAllocationMethod.setTaskGroupOrder(false);

        assertEquals(expectedTaskGroupOrder,taskAllocationMethod.getTaskQuery().toString().trim());
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
                "TASKS.COMPLETED, GROUP_CONCAT(SKILLS.SKILL), GROUP_CONCAT(TASK_SKILLS.PROFICIENCY_REQUIRED), PROJECTS.NAME " +
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
