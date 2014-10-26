package com.Hussain.pink.triangle.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hussain on 26/10/2014.
 */
public class WelcomeScreen extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(WelcomeScreen.class);
    
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
                LOG.debug("The user has decided to import CSV files");
                new ImportCSVView().setVisible(true);
                WelcomeScreen.this.dispose();
            }
        });

        allocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.debug("The user has decided to see the allocation view");
            }
        });
    }
}
