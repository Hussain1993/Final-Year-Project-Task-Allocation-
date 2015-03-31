package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.CSV.ExportCSV;
import com.Hussain.pink.triangle.Utils.FileIO;

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
    private JButton exportDatabaseButton;
    private JButton compareButton;

    public WelcomeScreen(){
        super("Task MatchingAlgorithms");
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

        exportDatabaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String folderToStoreCSVFiles = FileIO.chooseDirectory(WelcomeScreen.this);
                ExportCSV exportCSV = new ExportCSV(folderToStoreCSVFiles);
                exportCSV.exportDatabase();
            }
        });

        compareButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point locationOnScreen = WelcomeScreen.this.getLocationOnScreen();
                WelcomeScreen.this.dispose();
                CompareView compareView = new CompareView();
                compareView.setLocation(locationOnScreen);
                compareView.setVisible(true);
            }
        });
    }
}
