package com.Hussain.pink.triangle.CommandLine;

import com.Hussain.pink.triangle.Model.TaskAllocationConstants;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * Created by Hussain on 31/03/2015.
 */
public class BuildCommandLineOptions {
    private static final String ARGUMENT_NAME = "CSV File";

    public static Options buildCommandLineOptions(){
        Option help = new Option(TaskAllocationConstants.COMMAND_LINE_HELP,"print this message");

        Option gui = new Option(TaskAllocationConstants.COMMAND_LINE_GUI,"Start the application using the GUI");

        Option employeeFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Employee CSV File to be imported")
                                            .create(TaskAllocationConstants.COMMAND_LINE_EMPLOYEE_FILE);

        Option skillsFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Skills CSV File to be imported")
                                            .create(TaskAllocationConstants.COMMAND_LINE_SKILLS_FILE);

        Option projectFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Project CSV File to be imported")
                                            .create(TaskAllocationConstants.COMMAND_LINE_PROJECTS_FILE);

        Option tasksFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                        .hasArg().withDescription("Task CSV File to be imported")
                                        .create(TaskAllocationConstants.COMMAND_LINE_TASKS_FILE);

        Option employeeSkillsFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Employee Skills CSV File to be imported")
                                            .create(TaskAllocationConstants.COMMAND_LINE_EMPLOYEE_SKILLS);

        Option taskSkillsFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                        .hasArg().withDescription("Task Skills CSV File to be imported")
                                        .create(TaskAllocationConstants.COMMAND_LINE_TASKS_SKILLS);

        Option assignedToFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                .hasArg().withDescription("Assigned to CSV file to be imported")
                .create(TaskAllocationConstants.COMMAND_LINE_ASSIGNED_TO_FILE);

        Option usersFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                .hasArg().withDescription("Users CSV file to import")
                .create(TaskAllocationConstants.COMMAND_LINE_USERS_FILE);

        Options options = new Options();
        options.addOption(help);
        options.addOption(gui);
        options.addOption(employeeFile);
        options.addOption(skillsFile);
        options.addOption(projectFile);
        options.addOption(tasksFile);
        options.addOption(employeeSkillsFile);
        options.addOption(taskSkillsFile);
        options.addOption(assignedToFile);
        options.addOption(usersFile);
        return options;
    }
}
