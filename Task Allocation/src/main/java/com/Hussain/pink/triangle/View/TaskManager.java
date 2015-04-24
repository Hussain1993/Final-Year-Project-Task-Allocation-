package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.TaskManagerTableModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hussain on 24/04/2015.
 */
public class TaskManager extends JFrame {
    private JPanel root;
    private JPanel tablePanel;
    private JButton updateButton;
    private JButton backButton;

    private TaskManagerTableModel tableModel;

    public TaskManager(){
        super("Task Manager");
        setContentPane(root);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().setDefaultButton(updateButton);

        initTable();
        addActionListeners();
    }

    private void initTable(){
        tableModel = new TaskManagerTableModel();
        JTable taskManagerTable = new JTable(tableModel);
        taskManagerTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(taskManagerTable);
        tablePanel.add(scrollPane);
    }

    private void addActionListeners(){
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO
            }
        });
    }
}
