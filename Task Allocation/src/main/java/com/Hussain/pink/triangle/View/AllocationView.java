package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Allocation.GreedyTaskAllocation;
import com.Hussain.pink.triangle.Allocation.TaskAllocationMethod;
import com.Hussain.pink.triangle.Graph.Graph;
import com.Hussain.pink.triangle.Model.AllocationTableModel;
import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.Organisation.Employee;
import com.Hussain.pink.triangle.Organisation.Task;
import com.Hussain.pink.triangle.Utils.FileIO;
import com.Hussain.pink.triangle.Utils.TaskAllocationFile;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hussain on 11/11/2014.
 */
public class AllocationView extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(AllocationView.class);
    private static final String [] extensions = {"ta"};
    private static final String description = "Task Allocation Files";
    private static final int ASSIGN_TASK_COLUMN_INDEX = 4;

    private static final int GREEDY_ALPHABETICAL = 0;
    private static final int GREEDY_COST = 1;
    private static final int MAXIMUM_ALGORITHM = 2;

    private JPanel rootPanel;
    private JComboBox algorithmBox;
    private JButton doneButton;
    private JPanel tablePanel;
    private JButton assignButton;
    private JButton backButton;
    private JLabel statusLabel;
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
                if(!TaskAllocationFile.saveTaskAllocationFile(file+"."+extensions[0],tableModel))
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

                int selectedMethod = algorithmBox.getSelectedIndex();
                switch (selectedMethod)
                {
                    case GREEDY_ALPHABETICAL: greedyAllocation(GREEDY_ALPHABETICAL); break;
                    case GREEDY_COST: greedyAllocation(GREEDY_COST);break;
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
    }

    private void loadFileIntoTable(String filePath){
        ArrayList<Object[]> dataRows = TaskAllocationFile.parseTaskAllocationFile(filePath);
        for(Object[] row: dataRows)
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

    private void greedyAllocation(int greedyOrder){
        TaskAllocationMethod taskAllocationMethod = new GreedyTaskAllocation();
        switch (greedyOrder)
        {
            case GREEDY_ALPHABETICAL: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_NAME_ALPHABETICAL,TaskAllocationMethod.EMPLOYEE_QUERY); break;
            case GREEDY_COST: taskAllocationMethod.setQueryOrder(TaskAllocationMethod.ORDER_COST_LOW_TO_HIGH,TaskAllocationMethod.EMPLOYEE_QUERY); break;
        }
        ResultSet employeeResultSet = taskAllocationMethod.executeQuery(TaskAllocationMethod.EMPLOYEE_QUERY);
        ResultSet tasksResultSet = taskAllocationMethod.executeQuery(TaskAllocationMethod.TASK_QUERY);

        Graph<Employee,Task> taskAllocationGraph = taskAllocationMethod.buildGraph(employeeResultSet,tasksResultSet);
        taskAllocationMethod.allocateTasks(taskAllocationGraph);

        if(taskAllocationGraph.isEmpty())
        {
            LOG.info("No suitable employees could be found for the tasks");
        }
        else
        {
            LOG.info("An allocation for the tasks has been found, populating the allocation table");
            tableModel.setRowCount(0);//Clear the table every time there is a new allocation
            Set<Map.Entry<Employee,Task>> entrySet = taskAllocationGraph.getEmployeeToTaskMapping().entrySet();
            for (Map.Entry<Employee, Task> employeeTaskEntry : entrySet) {
                Employee e = employeeTaskEntry.getKey();
                Task t = employeeTaskEntry.getValue();
                Object [] rowData = {e.getId(),e.getName(),t.getTaskName(),t.getId(),false};
                tableModel.addRow(rowData);
            }
            statusLabel.setText("A suggested allocation can be found below");
        }
    }
}
