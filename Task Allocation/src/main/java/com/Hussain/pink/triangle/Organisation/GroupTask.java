package com.Hussain.pink.triangle.Organisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by Hussain on 17/02/2015.
 */
public class GroupTask {
    private static final Logger LOG = LoggerFactory.getLogger(GroupTask.class);

    private static final HashMap<Project,Integer> projectToTaskMapping = new HashMap<>();


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

    public static Set<Project> getUnassignedProjects(){
        return projectToTaskMapping.keySet();
    }
}
