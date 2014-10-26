package com.Hussain.pink.triangle.CSV;

import com.Hussain.pink.triangle.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Hussain on 24/10/2014.
 * This class will be use to import
 * the CSV file that the user has entered into the database
 */
public class ImportCSV {
    private static final Logger LOG = LoggerFactory.getLogger(ImportCSV.class);

    public static final int EMPLOYEES_TABLE = 1;
    public static final int SKILLS_TABLE = 2;
    public static final int PROJECTS_TABLE = 3;
    public static final int TASKS_TABLE = 4;
    public static final int EMPLOYEE_SKILLS_TABLE = 5;
    public static final int TASKS_SKILLS_TABLE = 6;

    private static final String PROPERTIES_FILENAME="TaskAllocation.properties";
    private static final String CLASSNAME_KEY = "classname";
    private static final String PASSWORD_KEY = "password";
    private static final String USERNAME_KEY = "username";
    private static final String URL_KEY = "url";

    /**
     * This method will take a CSV file and import it into
     * the Task Allocation Database
     * @param filePath The file path of the CSV file to be imported
     * @param table Integer representing the table that should be imported into</br>
     *              <ol>
     *                  <li>EMPLOYEES<li/>
     *                  <li>SKILLS<li/>
     *                  <li>PROJECTS<li/>
     *                  <li>TASKS<li/>
     *                  <li>EMPLOYEE SKILLS<li/>
     *                  <li>TASKS SKILLS<li/>
     *              <ol/>
     */
    public static int importCSV(String filePath, int table){
        Connection conn;
        if(!DBUtils.getInit())
        {
            try {
                DBUtils.init(PROPERTIES_FILENAME,CLASSNAME_KEY,PASSWORD_KEY,USERNAME_KEY,URL_KEY);
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
               LOG.error("There was an error initialising the connection to the database",e);
            }
        }
        try{
            conn = DBUtils.getConnection();
            if(conn != null && conn.isValid(0))
            {
                String query = "load data local infile \'%s\' into table %s fields terminated by \',\' lines terminated by \'\\n\' ignore 1 lines";
                switch(table){
                    case EMPLOYEES_TABLE: query = String.format(query,filePath,"EMPLOYEES");
                        break;
                    case SKILLS_TABLE: query = String.format(query, filePath,"SKILLS");
                        break;
                    case PROJECTS_TABLE: query = String.format(query,filePath,"PROJECTS");
                        break;
                    case TASKS_TABLE: query = String.format(query,filePath,"TASKS");
                        break;
                    case EMPLOYEE_SKILLS_TABLE: query = String.format(query,filePath,"EMPLOYEE_SKILLS");
                        break;
                    case TASKS_SKILLS_TABLE: query = String.format(query,filePath,"TASK_SKILLS");
                        break;
                }
                Statement stmt = conn.createStatement();
                return stmt.executeUpdate(query);
            }
        }
        catch(SQLException sqlException){
            LOG.error("There was an error with importing the CSV file {} into the database",filePath, sqlException);
        }
        return 0;
    }
}
