package com.Hussain.pink.triangle.Main;

import com.Hussain.pink.triangle.CommandLine.BuildCommandLineOptions;
import com.Hussain.pink.triangle.Model.TaskAllocationConstants;
import com.Hussain.pink.triangle.Threads.StartingThread;
import com.Hussain.pink.triangle.View.LogView;
import org.apache.commons.cli.*;
import org.apache.log4j.xml.DOMConfigurator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Security;

/**
 * This is where it all begins
 * Created by Hussain on 24/10/2014.
 */
public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());//Add the Bouncy Castle security provider at the start

        Options options = BuildCommandLineOptions.buildCommandLineOptions();
        CommandLineParser commandLineParser = new BasicParser();

        try {
            CommandLine commandLine = commandLineParser.parse(options,args);
            if(commandLine.hasOption(TaskAllocationConstants.COMMAND_LINE_HELP))
            {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("task allocation",options);
            }
            else if(commandLine.hasOption(TaskAllocationConstants.COMMAND_LINE_GUI))
            {
                try{
                    String propertiesFile = System.getProperty(TaskAllocationConstants.CONFIG_FILE_PATH_PROPERTY_KEY);
                    if(propertiesFile == null || propertiesFile.isEmpty())
                    {
                        throw new Exception("The system property: task.allocation.properties.file. has not been" +
                                " specified at start up. Re-run the application again with the command" +
                                " java -Dtask.allocation.properties.file=[PATH TO PROPERTIES FILE] -jar [JAR FILE]");
                    }
                    else if(System.getProperty("log4j.xml") == null)
                    {
                        throw new Exception("The name of the log4j file has not been specified. Re-run the application" +
                                " again with the command java -Dlog4j.xml=[PATH TO LOG4J.XML] -jar [JAR FILE]");
                    }
                    //This sets the filename used for LOG4J.
                    DOMConfigurator.configure(System.getProperty("log4j.xml"));
                    LogView.getInstance().setVisible(true);//Show the logging window
                    new Thread(new StartingThread()).start();
                }
                catch(Exception e){
                    LOG.error(e.getMessage());
                }
            }
        }
        catch (ParseException parseException) {
            LOG.error("There was an error while parsing the command line options");
        }
    }
}