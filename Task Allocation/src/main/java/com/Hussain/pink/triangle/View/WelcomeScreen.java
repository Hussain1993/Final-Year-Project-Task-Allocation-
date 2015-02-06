package com.Hussain.pink.triangle.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The welcome screen for the application, after the user has logged into the
 * system
 * Created by Hussain on 26/10/2014.
 */
public class WelcomeScreen extends JFrame{
    private JPanel rootPanel;
    private JButton importCSVsButton;
    private JButton allocationButton;

    public WelcomeScreen(){
        super("Task Allocation");
        setContentPane(rootPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addActionListeners();
        pack();
    }


    private void addActionListeners(){
        importCSVsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point locationOnScreen = WelcomeScreen.this.getLocationOnScreen();
                WelcomeScreen.this.dispose();
                ImportCSVView importCSVView = new ImportCSVView();
                importCSVView.setLocation(locationOnScreen);
                importCSVView.setVisible(true);
            }
        });

        allocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point locationOnScreen = WelcomeScreen.this.getLocationOnScreen();
                WelcomeScreen.this.dispose();
                AllocationView allocationView = new AllocationView();
                allocationView.setLocation(locationOnScreen);
                allocationView.setVisible(true);
            }
        });
    }
}
