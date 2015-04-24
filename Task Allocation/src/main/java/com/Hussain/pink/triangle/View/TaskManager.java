package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.TaskManagerTable;
import com.Hussain.pink.triangle.Model.TaskManagerTableModel;
import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Set;

/**
 * This is the view for the task manager
 * Created by Hussain on 24/04/2015.
 */
public class TaskManager extends JFrame {
    private static final Logger LOG = LoggerFactory.getLogger(TaskManager.class);

    private JPanel root;
    private JPanel tablePanel;
    private JButton updateButton;
    private JButton backButton;
    private JLabel numberOfTasksLabel;

    private TaskManagerTableModel tableModel;
    private TaskManagerTable taskManagerTable;

    private Connection conn;
    private PreparedStatement stmt;

    public TaskManager(){
        super("Task Manager");
        setContentPane(root);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(updateButton);

        initTable();
        addActionListeners();
        populateTable();
        pack();
    }

    /**
     * Make the table to be displayed to the user
     */
    private void initTable(){
        tableModel = new TaskManagerTableModel();
        taskManagerTable = new TaskManagerTable(tableModel);
        taskManagerTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(taskManagerTable);
        tablePanel.add(scrollPane);
    }

    /**
     * Add the action listeners for the buttons on screen
     */
    private void addActionListeners(){
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Integer> updatedTasks = taskManagerTable.getRowsEdited();
                String query = "UPDATE TASKS SET COMPLETED = ? WHERE ID = ?";
                try{
                    conn = DatabaseConnection.getDatabaseConnection();
                    if(conn != null)
                    {
                        stmt = conn.prepareStatement(query);
                        //Loop over every changed row and get the values from these rows
                        //to be updated in the database
                        for (int row : updatedTasks)
                        {
                            int taskId = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, 0)));
                            boolean completed = Boolean.parseBoolean(String.valueOf(tableModel.getValueAt(row, 5)));
                            stmt.setBoolean(1,completed);
                            stmt.setInt(2,taskId);
                            //Add these changes to the batch, to be executed later
                            stmt.addBatch();
                        }
                        //Execute the batch and log the number of tasks that have been updated
                        LOG.info("Number of updated tasks: {}",stmt.executeBatch().length);
                        //Clear the set that contains all the rows that have been updated
                        taskManagerTable.clearRowsEdited();
                    }
                }
                catch (SQLException e1) {
                    LOG.error("There was an error with the SQL Statement",e1);
                }
                finally {
                    DbUtils.closeQuietly(conn,stmt,null);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point locationOnScreen = TaskManager.this.getLocationOnScreen();
                TaskManager.this.dispose();
                WelcomeScreen welcomeScreen = new WelcomeScreen();
                welcomeScreen.setLocation(locationOnScreen);
                welcomeScreen.setVisible(true);
            }
        });
    }

    /**
     * Populate the table with the tasks information
     * that is in the database
     */
    private void populateTable(){
        ResultSet resultSet = null;
        try{
            resultSet = DatabaseQueries.getTasks();
            while(resultSet != null && resultSet.next())
            {
                Object[] rows = {};
                int id = resultSet.getInt(1);
                String taskName = resultSet.getString(2);
                int projectId = resultSet.getInt(3);
                Date dateFrom = resultSet.getDate(4);
                Date dateTo = resultSet.getDate(5);
                Boolean completed = resultSet.getBoolean(6);
                rows = ArrayUtils.add(rows,id);
                rows = ArrayUtils.add(rows,taskName);
                rows = ArrayUtils.add(rows,projectId);
                rows = ArrayUtils.add(rows,dateFrom);
                rows = ArrayUtils.add(rows,dateTo);
                rows = ArrayUtils.add(rows,completed);
                tableModel.addRow(rows);
            }
        }
        catch (SQLException e) {
            LOG.error("There was an error with the ResultSet",e);
        }
        finally {
            DatabaseQueries.closeConnectionToDatabase(resultSet);
        }
        //display the number of tasks within the database
        numberOfTasksLabel.setText("Number of Tasks: "+tableModel.getRowCount());
    }
}
