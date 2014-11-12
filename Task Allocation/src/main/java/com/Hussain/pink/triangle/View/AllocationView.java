package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Utils.FileIO;
import com.Hussain.pink.triangle.Utils.TaskAllocationFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

    private JPanel rootPanel;
    private JComboBox algorithmBox;
    private JButton doneButton;
    private JPanel tablePanel;
    private JButton assignButton;
    private DefaultTableModel tableModel;


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
        tableModel = new DefaultTableModel(null,columnNames);
        JTable allocationTable = new JTable(tableModel){

            @Override
            public Class getColumnClass(int column){
                switch (column)
                {
                    case 0: return String.class;
                    case 1: return String.class;
                    case 2: return String.class;
                    case 3: return String.class;
                    default: return Boolean.class;
                }
            }
        };
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
                tableModel.insertRow(0,new Object[] {"1","Hussain","t1",false});
            }
        });

        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
}
