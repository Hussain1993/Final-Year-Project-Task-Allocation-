package com.Hussain.pink.triangle.CommandLine;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

/**
 * Created by Hussain on 31/03/2015.
 */
public class BuildCommandLineOptions {
    private static final String ARGUMENT_NAME = "CSV File";

    public static Options buildCommandLineOptions(){
        Option help = new Option("Help","print this message");

        Option employeeFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Employee CSV File to be imported")
                                            .create("EmployeeFile");

        Option skillsFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Skills CSV File to be imported")
                                            .create("SkillsFile");

        Option projectFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Project CSV File to be imported")
                                            .create("ProjectsFile");

        Option tasksFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                        .hasArg().withDescription("Task CSV File to be imported")
                                        .create("TasksFile");

        Option employeeSkillsFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                            .hasArg().withDescription("Employee Skills CSV File to be imported")
                                            .create("EmployeeSkills");

        Option taskSkillsFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                                        .hasArg().withDescription("Task Skills CSV File to be imported")
                                        .create("TaskSkills");

        Option assignedToFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                .hasArg().withDescription("Assigned to CSV file to be imported")
                .create("AssignedToFile");

        Option usersFile = OptionBuilder.withArgName(ARGUMENT_NAME)
                .hasArg().withDescription("Users CSV file to import")
                .create("UsersFile");

        Options options = new Options();
        return options;
    }
}
