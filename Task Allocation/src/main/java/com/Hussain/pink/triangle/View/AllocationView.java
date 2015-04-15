package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.Allocation;
import com.Hussain.pink.triangle.Model.AllocationTableModel;
import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.Utils.FileIO;
import com.Hussain.pink.triangle.Utils.TaskAllocationFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * View to the show the allocation table to the user and for the user
 * to start a new allocation
 * Created by Hussain on 11/11/2014.
 */
public class AllocationView extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(AllocationView.class);
    private static final String extension = "ta";
    private static final String description = "Task MatchingAlgorithms Files";

    private static final int GREEDY = 0;
    private static final int MAXIMUM = 1;

    private JPanel rootPanel;
    private JComboBox algorithmBox;
    private JButton doneButton;
    private JPanel tablePanel;
    private JButton assignButton;
    private JButton backButton;
    private JButton advancedOptionsButton;
    private AllocationTableModel tableModel;

    public AllocationView() {
        super("Allocation View");
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(doneButton);
        addActionListeners();
        initTable();
        initMenu();
        pack();
    }

    /**
     * Make a new empty table using a custom model
     */
    private void initTable(){
        tableModel = new AllocationTableModel();
        JTable allocationTable = new JTable(tableModel);
        allocationTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(allocationTable);
        tablePanel.add(scrollPane);
    }

    /**
     * Initialises the JMenu for the window
     */
    private void initMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenuItem open = new JMenuItem("Open...");
        JMenuItem save = new JMenuItem("Save");

        fileMenu.add(open);
        fileMenu.add(save);

        this.setJMenuBar(menuBar);

        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = FileIO.openFileDialog(AllocationView.this, extension, description,FileIO.OPEN_MODE);
                if (file != null)
                {
                    loadFileIntoTable(file);
                }
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = FileIO.openFileDialog(AllocationView.this, extension,description,FileIO.SAVE_MODE);
                if(file != null && !TaskAllocationFile.saveTaskAllocationFile(file+"."+extension,tableModel))
                {
                    LOG.error("There was an error while saving the file {}", file);
                }
            }
        });
    }

    /**
     * Adds all the action listeners for all the buttons on the
     * window
     */
    private void addActionListeners() {
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                switch (algorithmBox.getSelectedIndex())
                {
                    case GREEDY: populateTable(new Allocation(GREEDY).allocateEmployeesAndTasks()); break;
                    case MAXIMUM: populateTable(new Allocation(MAXIMUM).allocateEmployeesAndTasks()); break;
                    default: populateTable(new Allocation(GREEDY).allocateEmployeesAndTasks()); break;
                }
            }
        });

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.info("The number of employees that have been assigned tasks is: {}",DatabaseQueries.assignTasksToEmployees(tableModel));
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point locationOnScreen = AllocationView.this.getLocationOnScreen();
                AllocationView.this.dispose();
                WelcomeScreen welcomeScreen = new WelcomeScreen();
                welcomeScreen.setLocation(locationOnScreen);
                welcomeScreen.setVisible(true);
            }
        });

        advancedOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdvancedOptionDialog().setVisible(true);
            }
        });
    }

    /**
     * Load a new task allocation file into the table
     * @param filePath To the task allocation file
     */
    private void loadFileIntoTable(String filePath){
        ArrayList<Object[]> dataRows = TaskAllocationFile.parseTaskAllocationFile(filePath);
        //Clear the table before we show the suggested allocation to the user
        tableModel.setRowCount(0);
        for(Object[] row: dataRows)
        {
            tableModel.addRow(row);
        }
    }

    /**
     * Takes the rows that have been returned from the task allocation method
     * and displays it to the user
     * @param dataRows Row to be displayed
     */
    private void populateTable(ArrayList<Object[]> dataRows){
        //Clear the table every time there is a new allocation
        tableModel.setRowCount(0);
        if(dataRows.size() == 0)
        {
            LOG.info("No suitable employees could be found for the tasks");
            return;
        }
        for (Object[] dataRow : dataRows) {
            tableModel.addRow(dataRow);
        }
    }
}
