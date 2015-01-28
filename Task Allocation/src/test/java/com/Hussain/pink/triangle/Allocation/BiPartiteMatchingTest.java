package com.Hussain.pink.triangle.Allocation;

/**
 * Created by Hussain on 26/01/2015.
 */
public class BiPartiteMatchingTest {


//    @Test
//    public void testAllocateTasks() {
//        Graph<Employee, Task> testGraph = new Graph<>();
//
//        final Skill java = new Skill("Java",1);
//        final Skill uml = new Skill("UML",1);
//        final Skill iOS = new Skill("iOS",1);
//
//        LinkedHashSet<Skill> e1SkillSet = new LinkedHashSet<Skill>(){{add(java); add(uml); add(iOS);}};
//        LinkedHashSet<Skill> e2SkillSet = new LinkedHashSet<Skill>(){{add(iOS);}};
//
//        LinkedHashSet<Skill> t1SkillSet = new LinkedHashSet<Skill>(){{add(java); add(uml);}};
//        LinkedHashSet<Skill> t2SkillSet = new LinkedHashSet<Skill>(){{add(iOS);}};
//
//        Employee e1 = new Employee(1,"e1",e1SkillSet,0);
//        Employee e2 = new Employee(2,"e2",e2SkillSet,0);
//
//        final Task t1 = new Task(1,"t1",1,1L,1L,false,t1SkillSet);
//        final Task t2 = new Task(2,"t2",1,1L,1L,false,t2SkillSet);
//
//        Set<Task> tasksThatShouldBeMappedToE1 = new LinkedHashSet<Task>(){{add(t1); add(t2);}};
//        Set<Task> tasksThatShouldBeMappedToE2 = new LinkedHashSet<Task>(){{add(t2);}};
//
//
//        //Add the employees and tasks to the graph
//        testGraph.addEmployeeNode(e1);
//        testGraph.addEmployeeNode(e2);
//
//        testGraph.addTaskNode(t1);
//        testGraph.addTaskNode(t2);
//
//        TaskAllocationMethod taskAllocationMethod = new BiPartiteMatching();
//
//        taskAllocationMethod.allocateTasks(testGraph);
//
//        assertTrue(testGraph.getMappedTask(e1).size() == 2);//Check that two tasks have been assigned to e1
//        assertTrue(testGraph.getMappedTask(e2).size() == 1);//Check that only one task has been mapped to e2
//
//        assertTrue(testGraph.getMappedTask(e1).containsAll(tasksThatShouldBeMappedToE1)
//        && testGraph.getMappedTask(e2).containsAll(tasksThatShouldBeMappedToE2));
//    }
}
