package com.Hussain.pink.triangle.CSV;

import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import com.Hussain.pink.triangle.utils.PropsGetter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This is class that will export the data
 * that is contained in the database to CSV files.
 * A new file will be created for each table
 * Created by Hussain on 11/02/2015.
 */
public class ExportCSV {
    private static final Logger LOG = LoggerFactory.getLogger(ExportCSV.class);

    private String folderToSaveCSVFiles;
    private boolean exportEmptyTables;

    /**
     *
     * @param folderToSaveCSVFiles This is the folder the user would like
     *                             save all the csv files to
     */
    public ExportCSV(String folderToSaveCSVFiles){
        this.folderToSaveCSVFiles = folderToSaveCSVFiles;
        PropsGetter properties = PropsGetter.getInstance();
        this.exportEmptyTables = Boolean.parseBoolean(properties.getProperty("export.empty.table"));
    }

    /**
     * This method will create a thread for each table export and will start each thread in
     * turn to start the export process. The method uses the <code>CyclicBarrier</code> to make
     * sure that all the threads start at roughly the same time
     */
    public void exportDatabase(){
        final CyclicBarrier gate = new CyclicBarrier(9);


        Thread assignedTo = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportAssignedTo())
                {
                    LOG.error("There was an error when saving the ASSIGNED_TO csv file");
                }
            }
        };

        Thread employees = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportEmployees())
                {
                    LOG.error("There was an error when saving the EMPLOYEES csv file");
                }
            }
        };

        Thread employeeSkills = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportEmployeeSkills())
                {
                    LOG.error("There was an error when saving the EMPLOYEE_SKILLS csv file");
                }
            }
        };

        Thread projects = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportProjects())
                {
                    LOG.error("There was an error when saving the PROJECTS csv file");
                }
            }
        };

        Thread skills = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportSkills())
                {
                    LOG.error("There was an error when saving the SKILLS csv file");
                }
            }
        };

        Thread tasks = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportTasks())
                {
                    LOG.error("There was an error when saving the TASKS csv file");
                }
            }
        };

        Thread taskSkills = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportTaskSkills())
                {
                    LOG.error("There was an error when saving the TASK_SKILLS csv file");
                }
            }
        };

        Thread users = new Thread(){
            @Override
            public void run() {
                try{
                    gate.await();
                }
                catch (InterruptedException | BrokenBarrierException e) {
                    LOG.error("There was an error with the thread",e);
                }
                if(!ExportCSV.this.exportUsers())
                {
                    LOG.error("There was an error when saving the USERS csv file");
                }
            }
        };

        assignedTo.start();
        employees.start();
        employeeSkills.start();
        projects.start();
        skills.start();
        tasks.start();
        taskSkills.start();
        users.start();

        try{
            gate.await();//This starts all the threads
            assignedTo.join();
            employees.join();
            employeeSkills.join();
            projects.join();
            skills.join();
            tasks.join();
            taskSkills.join();
            users.join();
        }
        catch (InterruptedException | BrokenBarrierException e) {
            LOG.error("There was an error with a thread",e);
        }
        LOG.info("Database has been exported to the folder {}",folderToSaveCSVFiles);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportAssignedTo(){
        String query = "SELECT * FROM ASSIGNED_TO";
        String filePath = folderToSaveCSVFiles + File.separator + "ASSIGNED_TO.csv";
        return executeQuery(query,filePath);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportEmployees(){
        String query = "SELECT * FROM EMPLOYEES";
        String filePath = folderToSaveCSVFiles + File.separator + "EMPLOYEES.csv";
        return executeQuery(query,filePath);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportEmployeeSkills(){
        String query = "SELECT * FROM EMPLOYEE_SKILLS";
        String filePath = folderToSaveCSVFiles + File.separator + "EMPLOYEE_SKILLS.csv";
        return executeQuery(query,filePath);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportProjects(){
        String query = "SELECT * FROM PROJECTS";
        String filePath = folderToSaveCSVFiles + File.separator + "PROJECTS.csv";
        return executeQuery(query,filePath);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportSkills(){
        String query = "SELECT * FROM SKILLS";
        String filePath = folderToSaveCSVFiles + File.separator + "SKILLS.csv";
        return executeQuery(query,filePath);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportTasks(){
        String query = "SELECT * FROM TASKS";
        String filePath = folderToSaveCSVFiles + File.separator + "TASKS.csv";
        return executeQuery(query,filePath);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportTaskSkills(){
        String query = "SELECT * FROM TASK_SKILLS";
        String filePath = folderToSaveCSVFiles + File.separator + "TASK_SKILLS.csv";
        return executeQuery(query,filePath);
    }

    /**
     * Export the ASSIGNED_TO table
     * @return true if the file has been created and saved, false otherwise
     */
    private boolean exportUsers(){
        String query = "SELECT * FROM USERS";
        String filePath = folderToSaveCSVFiles + File.separator + "USERS.csv";
        return executeQuery(query, filePath);
    }

    /**
     * Executes a query and saves the output
     * to a CSV file
     * @param query The query to execute
     * @param filePath The file path to save the file to
     * @return true if the file has been created, false otherwise
     */
    private boolean executeQuery(String query, String filePath){
        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CSVPrinter printer = null;
        try{
            stmt = conn.prepareStatement(query);
            resultSet = stmt.executeQuery();
            boolean resultSetEmpty = resultSetIsEmpty(resultSet);
            if(resultSetEmpty && exportEmptyTables)
            {
                exportDatabaseToFile(filePath,resultSet, printer);
            }
            else if(resultSetEmpty)
            {
                return true;
            }
            else
            {
                exportDatabaseToFile(filePath,resultSet, printer);
            }

        }
        catch (SQLException e) {
            LOG.error("There was an error when trying to export the databases",e);
        }
        catch (IOException e) {
            LOG.error("There was an error creating the file",filePath);
        } finally {
            DbUtils.closeQuietly(conn,stmt,resultSet);
            IOUtils.closeQuietly(printer);
        }
        return new File(filePath).exists();
    }

    /**
     * Utility method to check is the ResultSet object is empty
     * @param resultSet The ResultSet object to check
     * @return True if the result set is empty, false otherwise
     * @throws SQLException
     */
    private boolean resultSetIsEmpty(ResultSet resultSet) throws SQLException{
        return !resultSet.first();
    }

    /**
     * This method writes the result set object to a CSV file
     * @param filePath The path to save the CSV file
     * @param resultSet The result set object to write
     * @param printer The printer object to use when writing the file
     * @throws SQLException
     * @throws IOException
     */
    private void exportDatabaseToFile(String filePath, ResultSet resultSet, CSVPrinter printer) throws SQLException, IOException{
        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(new File(filePath)));
        printer = CSVFormat.MYSQL.withDelimiter(',').withHeader(resultSet).print(fileWriter);
        printer.printRecords(resultSet);
    }
}
