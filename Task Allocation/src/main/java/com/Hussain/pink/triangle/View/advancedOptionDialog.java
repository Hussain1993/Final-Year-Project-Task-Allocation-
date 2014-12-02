package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Model.AdvancedOptions;
import org.apache.commons.lang3.BooleanUtils;

import javax.swing.*;
import java.awt.event.*;

public class advancedOptionDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox tasksOrderComboBox;
    private JComboBox employeeAssignedTaskComboBox;
    private JComboBox comboBox1;
    private JComboBox comboBox2;

    public advancedOptionDialog() {
        setContentPane(contentPane);
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
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
    }

    private void onOK() {
        AdvancedOptions.setEmployeeAssignedTasks(BooleanUtils.toBoolean(employeeAssignedTaskComboBox.getSelectedItem().toString()));
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
