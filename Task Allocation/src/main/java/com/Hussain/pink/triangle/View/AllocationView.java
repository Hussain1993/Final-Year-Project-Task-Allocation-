package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.Allocation;
import com.Hussain.pink.triangle.Model.AllocationTableModel;
import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.Utils.FileIO;
import com.Hussain.pink.triangle.Utils.TaskAllocationFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
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
    private static final String description = "Task Allocation Files";
    private static final int ASSIGN_TASK_COLUMN_INDEX = 4;

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
        super("Allocation");
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
                LOG.info("The number of employees that have been assigned tasks is: {}",assignRows());
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllocationView.this.dispose();
                new WelcomeScreen().setVisible(true);
            }
        });

        advancedOptionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new advancedOptionDialog().setVisible(true);
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
     * This method will check which rows have been marked by the user
     * to assign the task to the employee and this information to be reflected back into the
     * database
     * @return The number of rows the user has decided to assign tasks to
     */
    public int assignRows(){
        ArrayList<int []> employeeTaskRows = new ArrayList<>();
        int employeeIDColumnIndex = 0;
        int taskIDColumnIndex = 3;

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if((Boolean) tableModel.getValueAt(row,ASSIGN_TASK_COLUMN_INDEX))
            {
               //We have a employee that the user would like to assign to a task
                //Get the employee ID and the task ID
                int employeeID = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, employeeIDColumnIndex)));
                int taskID = Integer.parseInt(String.valueOf(tableModel.getValueAt(row,taskIDColumnIndex)));
                int [] rowData = {employeeID,taskID};
                employeeTaskRows.add(rowData);
            }
        }
        if(employeeTaskRows.size() > 0)
        {
            return DatabaseQueries.assignTasksToEmployees(employeeTaskRows);
        }
        else
        {
            LOG.info("You have not assigned any tasks to employees");
            return 0;
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
