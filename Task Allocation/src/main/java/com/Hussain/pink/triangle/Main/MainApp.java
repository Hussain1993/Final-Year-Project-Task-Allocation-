package com.Hussain.pink.triangle.Main;

import com.Hussain.pink.triangle.Model.TaskAllocationConstants;
import com.Hussain.pink.triangle.Threads.StartingThread;
import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import com.Hussain.pink.triangle.View.LogView;
import org.apache.log4j.xml.DOMConfigurator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Security;
import java.sql.Connection;

/**
 * This is where it all begins
 * Created by Hussain on 24/10/2014.
 */
public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        checkSystemProperties();
        Security.addProvider(new BouncyCastleProvider());//Add the Bouncy Castle security provider at the start
        LogView.getInstance().setVisible(true);//Show the logging window
        new Thread(new StartingThread()).start();
    }


    /**
     * This method makes sure that the user has entered the
     * required system properties before the application starts
     */
    private static void checkSystemProperties(){
        try{
            String propertiesFilePath = System.getProperty(TaskAllocationConstants.CONFIG_FILE_PATH_PROPERTY_KEY);
            if(propertiesFilePath == null || propertiesFilePath.isEmpty())
            {
                throw new Exception("The system property: task.allocation.properties.file. has not been" +
                        " specified at start up. Re-run the application again with the command" +
                        " java -Dtask.allocation.properties.file=[PATH TO PROPERTIES FILE] -jar [JAR FILE]");
            }
            String log4jFile = System.getProperty(TaskAllocationConstants.LOG4J_XML_FILE_PATH_PROPERTY_KEY);
            if(log4jFile == null || log4jFile.isEmpty())
            {
                throw new Exception("The name of the log4j file has not been specified. Re-run the application" +
                        " again with the command java -Dlog4j.xml=[PATH TO LOG4J.XML] -jar [JAR FILE]");
            }
            checkConnectionToDatabase();
            DOMConfigurator.configure(log4jFile);
        }
        catch(Exception e){
            LOG.error(e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Check if MySQL is running and the application is able to
     * establish a connection to the database
     * @throws Exception When MySQL is not running or there is
     * a problem with the connection
     */
    private static void checkConnectionToDatabase() throws Exception{
        Connection conn = DatabaseConnection.getDatabaseConnection();
        if(conn == null)
        {
            throw new Exception("There was an error when getting a connection to the database" +
                    " please check that MySQL is running before opening the application again");
        }
    }
}