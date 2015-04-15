package com.Hussain.pink.triangle.Organisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Set;

/**
 * This class will be used to count the number of tasks that are outstanding for the
 * given project.
 * This class will hold a map containing the project as the key and this object will map
 * to the number of tasks that are remaining for the project
 * Created by Hussain on 17/02/2015.
 */
public class GroupTask {
    private static final Logger LOG = LoggerFactory.getLogger(GroupTask.class);

    private static final HashMap<Project,Integer> projectToTaskMapping = new HashMap<>();


    /**
     * Adds a new task to a project
     * @param project The project to add, if the map does not contain this project,
     *                then a new project will be added to the map and will be initialised to the value of one.
     */
    public static void addNewTaskToGroup(Project project){
        if(projectToTaskMapping.containsKey(project))
        {
            int numberOfTasks = projectToTaskMapping.get(project);
            numberOfTasks++;
            projectToTaskMapping.put(project,numberOfTasks);
        }
        else
        {
            projectToTaskMapping.put(project,1);
        }
    }

    /**
     * Removes a task from a project
     * @param project The project to remove the task from
     */
    public static void removeTaskFromGroup(Project project){
        if (projectToTaskMapping.containsKey(project))
        {
            int numberOfTasks = projectToTaskMapping.get(project);
            numberOfTasks--;
            if(numberOfTasks == 0)
            {
                //Remove this project from the map as we have assigned all
                //tasks that belong to this project
                projectToTaskMapping.remove(project);
                LOG.info("All the tasks for the project {} have been assigned",project.getProjectName());
            }
            else
            {
                projectToTaskMapping.put(project,numberOfTasks);//The updated number of tasks remaining
            }
        }
    }

    /**
     * Gets the number of outstanding tasks that are remaining for a given task
     * @param project The project to remove a task from
     * @return The number of tasks that are left, if the map does not contain the project, then it will return
     * 0 as a default
     */
    public static int getNumberOfMappedTasksForProject(Project project){
        if(!projectToTaskMapping.containsKey(project))
        {
            return 0;
        }
        else
        {
            return projectToTaskMapping.get(project);
        }
    }

    /**
     * Get a set of all the projects that are left in the map
     * @return A set of all projects that still have at least one task outstanding
     */
    public static Set<Project> getUnassignedProjects(){
        return projectToTaskMapping.keySet();
    }
}
