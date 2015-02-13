package com.Hussain.pink.triangle.CSV;

import au.com.bytecode.opencsv.CSVWriter;
import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Hussain on 11/02/2015.
 */
public class ExportCSV {
    private static final Logger LOG = LoggerFactory.getLogger(ExportCSV.class);

    private String folderToSaveCSVFiles;

    public ExportCSV(String folderToSaveCSVFiles){
        this.folderToSaveCSVFiles = folderToSaveCSVFiles;
    }

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

    private boolean exportAssignedTo(){
        String query = "SELECT * FROM ASSIGNED_TO";
        String filePath = folderToSaveCSVFiles + File.separator + "ASSIGNED_TO.csv";
        return executeQuery(query,filePath);
    }

    private boolean exportEmployees(){
        String query = "SELECT * FROM EMPLOYEES";
        String filePath = folderToSaveCSVFiles + File.separator + "EMPLOYEES.csv";
        return executeQuery(query,filePath);
    }

    private boolean exportEmployeeSkills(){
        String query = "SELECT * FROM EMPLOYEE_SKILLS";
        String filePath = folderToSaveCSVFiles + File.separator + "EMPLOYEE_SKILLS.csv";
        return executeQuery(query,filePath);
    }

    private boolean exportProjects(){
        String query = "SELECT * FROM PROJECTS";
        String filePath = folderToSaveCSVFiles + File.separator + "PROJECTS.csv";
        return executeQuery(query,filePath);
    }

    private boolean exportSkills(){
        String query = "SELECT * FROM SKILLS";
        String filePath = folderToSaveCSVFiles + File.separator + "SKILLS.csv";
        return executeQuery(query,filePath);
    }

    private boolean exportTasks(){
        String query = "SELECT * FROM TASKS";
        String filePath = folderToSaveCSVFiles + File.separator + "TASKS.csv";
        return executeQuery(query,filePath);
    }

    private boolean exportTaskSkills(){
        String query = "SELECT * FROM TASK_SKILLS";
        String filePath = folderToSaveCSVFiles + File.separator + "TASK_SKILLS.csv";
        return executeQuery(query,filePath);
    }

    private boolean exportUsers(){
        String query = "SELECT * FROM USERS";
        String filePath = folderToSaveCSVFiles + File.separator + "USERS.csv";
        return executeQuery(query,filePath);
    }

    private boolean executeQuery(String query, String filePath){
        Connection conn = DatabaseConnection.getDatabaseConnection();
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        CSVWriter csvWriter = null;
        try{
            stmt = conn.prepareStatement(query);
            resultSet = stmt.executeQuery();

            csvWriter = new CSVWriter(new FileWriter(filePath),',',CSVWriter.NO_QUOTE_CHARACTER);
            csvWriter.writeAll(resultSet,true);
        }
        catch (SQLException e) {
            LOG.error("There was an error when trying to export the ASSIGNED_TO table",e);
        }
        catch (IOException e) {
            LOG.error("There was an error with the ASSIGNED_TO.csv file",e);
        }
        finally {
            DbUtils.closeQuietly(conn,stmt,resultSet);
            IOUtils.closeQuietly(csvWriter);
        }
        return new File(filePath).exists();
    }
}
