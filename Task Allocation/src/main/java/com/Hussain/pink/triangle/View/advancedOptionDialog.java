package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import com.Hussain.pink.triangle.Model.OrderType;
import org.apache.commons.lang3.BooleanUtils;

import javax.swing.*;
import java.awt.event.*;

/**
 * This is the view where the user will be able to specify the
 * advanced options they can use when querying for the employees and tasks
 */
public class advancedOptionDialog extends JDialog {
    private JPanel rootPanel;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox tasksOrderComboBox;
    private JComboBox employeeAssignedTaskComboBox;
    private JComboBox orderOfEmployeesComboBox;
    private JComboBox groupTasksComboBox;
    private JCheckBox heuristicCheckBox;

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

        boolean checkIfEmployeesAreAssignedToTasks = AdvancedOptions.checkIfEmployeesAreAssignedToTasks();
        if(checkIfEmployeesAreAssignedToTasks)
        {
            employeeAssignedTaskComboBox.setSelectedIndex(1);
        }
        else
        {
            employeeAssignedTaskComboBox.setSelectedIndex(0);
        }
        OrderType employeeOrderType = AdvancedOptions.getEmployeeOrder();
        switch (employeeOrderType)
        {
            case NAME_ALPHABETICAL: orderOfEmployeesComboBox.setSelectedIndex(0);break;
            case NAME_REVERSE_ALPHABETICAL: orderOfEmployeesComboBox.setSelectedIndex(1); break;
            case COST_ASCENDING: orderOfEmployeesComboBox.setSelectedIndex(2); break;
            case COST_DESCENDING: orderOfEmployeesComboBox.setSelectedIndex(3); break;
            default: orderOfEmployeesComboBox.setSelectedIndex(0);break;
        }

        OrderType tasksOrderType = AdvancedOptions.getTasksOrder();
        switch (tasksOrderType)
        {
            case NONE: tasksOrderComboBox.setSelectedIndex(0);break;
            case NAME_ALPHABETICAL: tasksOrderComboBox.setSelectedIndex(1); break;
            case NAME_REVERSE_ALPHABETICAL: tasksOrderComboBox.setSelectedIndex(2); break;
            default: tasksOrderComboBox.setSelectedIndex(0); break;
        }

        boolean groupTasksByProject = AdvancedOptions.groupTasksByProject();
        if(groupTasksByProject)
        {
            groupTasksComboBox.setSelectedIndex(1);
        }
        else
        {
            groupTasksComboBox.setSelectedIndex(0);
        }
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
