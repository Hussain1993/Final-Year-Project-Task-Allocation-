package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.AllocationTableModel;
import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.Utils.FileIO;
import com.Hussain.pink.triangle.Utils.TaskAllocationFile;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by Hussain on 11/11/2014.
 */
public class AllocationView extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(AllocationView.class);
    private static final String [] extensions = {"ta"};
    private static final String description = "Task Allocation Files";
    private static final int ASSIGN_TASK_COLUMN_INDEX = 4;

    private JPanel rootPanel;
    private JComboBox algorithmBox;
    private JButton doneButton;
    private JPanel tablePanel;
    private JButton assignButton;
    private AllocationTableModel tableModel;


    private String [] columnNames = {"ID","Employee Name","Allocated Task","Task ID","Assign"};

    public AllocationView() {
        super("Allocation");
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addActionListeners();
        initTable();
        initMenu();
        pack();
    }

    private void initTable(){
        tableModel = new AllocationTableModel(null,columnNames);
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
                String file = FileIO.openFileDialog(AllocationView.this, extensions, description,FileIO.OPEN_MODE);
                loadFileIntoTable(file);
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = FileIO.openFileDialog(AllocationView.this,extensions,description,FileIO.SAVE_MODE);
                if(!TaskAllocationFile.saveTaskAllocationFile(file+".ta",tableModel))
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
                tableModel.insertRow(0,new Object[] {"1","Hussain","t1","1",false});
            }
        });

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.info("The number of employees that have been assigned tasks is: {}",assignRows());
            }
        });
    }

    private void loadFileIntoTable(String filePath){
        ArrayList<String[]> dataRows = TaskAllocationFile.parseTaskAllocationFile(filePath);
        for(String[] row: dataRows)
        {
            tableModel.addRow(row);
        }
    }

    public int assignRows(){
        ArrayList<int []> employeeTaskRows = new ArrayList<>();
        int [] rowData = {};
        int employeeIDColumnIndex = 0;
        int taskIDColumnIndex = 3;

        for (int row = 0; row < tableModel.getRowCount(); row++) {
            if((Boolean) tableModel.getValueAt(row,ASSIGN_TASK_COLUMN_INDEX))
            {
               //We have a employee that the user would like to assign to a task
                //Get the employee ID and the task ID
                int employeeID = Integer.parseInt(String.valueOf(tableModel.getValueAt(row, employeeIDColumnIndex)));
                int taskID = Integer.parseInt(String.valueOf(tableModel.getValueAt(row,taskIDColumnIndex)));
                rowData = ArrayUtils.add(rowData,employeeID);
                rowData = ArrayUtils.add(rowData,taskID);
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
}
