package com.Hussain.pink.triangle.CSV;

import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
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
        Connection conn = null;
        Statement stmt = null;
        try{
            conn = DatabaseConnection.getDatabaseConnection();
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
            stmt = conn.createStatement();
            return stmt.executeUpdate(query);
        }
        catch(SQLException sqlException){
            LOG.error("There was an error with importing the CSV file {} into the database",filePath, sqlException);
        }
        finally {
            DbUtils.closeQuietly(conn,stmt,null);
        }
        return 0;
    }
}
