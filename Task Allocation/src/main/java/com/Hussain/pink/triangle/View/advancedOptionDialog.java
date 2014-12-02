package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.OrderType;
import org.apache.commons.lang3.BooleanUtils;

import javax.swing.*;
import java.awt.event.*;

public class advancedOptionDialog extends JDialog {
    private JPanel rootPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox tasksOrderComboBox;
    private JComboBox employeeAssignedTaskComboBox;
    private JComboBox orderOfEmployeesComboBox;
    private JComboBox groupTasksComboBox;

    public advancedOptionDialog() {
        setContentPane(rootPanel);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        rootPanel.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
    }

    private void onOK() {
        //Do the employee settings first
        AdvancedOptions.setCheckIfEmployeesAreAssignedToTasks(
                BooleanUtils.toBoolean(employeeAssignedTaskComboBox.getSelectedItem().toString()));
        int orderOfEmployees = orderOfEmployeesComboBox.getSelectedIndex();
        switch (orderOfEmployees)
        {
            case 0: AdvancedOptions.setEmployeeOrder(OrderType.NAME_ALPHABETICAL);break;
            case 1: AdvancedOptions.setEmployeeOrder(OrderType.NAME_REVERSE_ALPHABETICAL); break;
            case 2: AdvancedOptions.setEmployeeOrder(OrderType.COST_ASCENDING);break;
            case 3: AdvancedOptions.setEmployeeOrder(OrderType.COST_DESCENDING);break;
            default: AdvancedOptions.setEmployeeOrder(OrderType.NAME_ALPHABETICAL); break;
        }

        //Do the Tasks advanced setting
        int tasksOrder = tasksOrderComboBox.getSelectedIndex();
        switch (tasksOrder)
        {
            case 0: AdvancedOptions.setTasksOrder(OrderType.NONE);break;
            case 1: AdvancedOptions.setTasksOrder(OrderType.NAME_ALPHABETICAL); break;
            case 2: AdvancedOptions.setTasksOrder(OrderType.NAME_REVERSE_ALPHABETICAL); break;
            default: AdvancedOptions.setTasksOrder(OrderType.NONE);
        }

        AdvancedOptions.setGroupTasksByProject(BooleanUtils.toBoolean(groupTasksComboBox.getSelectedItem().toString()));

        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
