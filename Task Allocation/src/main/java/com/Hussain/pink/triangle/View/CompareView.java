package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.AllocationTableModel;
import com.Hussain.pink.triangle.Utils.FileIO;
import com.Hussain.pink.triangle.Utils.TaskAllocationFile;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Hussain on 24/02/2015.
 */
public class CompareView extends JFrame {
    private static final Logger LOG = LoggerFactory.getLogger(CompareView.class);
    private static final String extension = "ta";
    private static final String description = "Task Allocation Files";
    private static final int TABLE_ONE = 1;
    private static final int TABLE_TWO = 2;

    private JPanel rootPanel;
    private JButton browseButton1;
    private JButton browseButton2;
    private JLabel fileNameLabel1;
    private JLabel fileNameLabel2;
    private JPanel tablePanel1;
    private JPanel tablePanel2;

    private AllocationTableModel tableModel1;
    private AllocationTableModel tableModel2;

    public CompareView(){
        super("Compare");
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initTable();
        addActionListeners();
        pack();
    }

    private void initTable(){
        tableModel1 = new AllocationTableModel();
        tableModel2 = new AllocationTableModel();

        JTable table1 = new JTable(tableModel1);
        JTable table2 = new JTable(tableModel2);

        table1.getTableHeader().setReorderingAllowed(false);
        table2.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane1 = new JScrollPane(table1);
        JScrollPane scrollPane2 = new JScrollPane(table2);

        tablePanel1.add(scrollPane1);
        tablePanel2.add(scrollPane2);
    }

    private void addActionListeners(){
        browseButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = FileIO.openFileDialog(CompareView.this,extension,description,FileIO.OPEN_MODE);
                if(file != null)
                {
                    String fileName = FilenameUtils.getBaseName(file);
                    fileNameLabel1.setText(fileName);
                    loadFileIntoTable(file, TABLE_ONE);
                }
            }
        });

        browseButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file = FileIO.openFileDialog(CompareView.this,extension,description,FileIO.OPEN_MODE);
                if(file != null)
                {
                    String fileName = FilenameUtils.getBaseName(file);
                    fileNameLabel2.setText(fileName);
                    loadFileIntoTable(file,TABLE_TWO);
                }
            }
        });
    }

    private void loadFileIntoTable(String filePath, int table){
        ArrayList<Object[]> dataRows = TaskAllocationFile.parseTaskAllocationFile(filePath);
        //Clear the correct table and add to the right table
        switch (table)
        {
            case 1: tableModel1.setRowCount(0);
                for(Object[] row : dataRows)
                {
                    tableModel1.addRow(row);
                }
                break;
            case 2: tableModel2.setRowCount(0);
                for(Object[] row : dataRows)
                {
                    tableModel2.addRow(row);
                }
                break;
        }
    }
}
