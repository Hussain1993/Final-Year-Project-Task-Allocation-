package com.Hussain.pink.triangle.View;

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
        //AdvancedOptions.setEmployeeAssignedTasks(BooleanUtils.toBoolean(employeeAssignedTaskComboBox.getSelectedItem().toString()));
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
