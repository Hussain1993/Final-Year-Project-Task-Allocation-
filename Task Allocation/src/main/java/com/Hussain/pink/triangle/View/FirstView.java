package com.Hussain.pink.triangle.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is the fist view that the user will see
 * Created by Hussain on 13/01/2015.
 */
public class FirstView  extends JFrame{
    private JPanel root;
    private JButton registerButton;
    private JButton loginButton;


    public FirstView(){
        super("Task Allocation");
        setContentPane(root);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addActionListeners();
        pack();
    }


    private void addActionListeners(){
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point locationOnScreen = FirstView.this.getLocationOnScreen();
                FirstView.this.dispose();
                RegisterView registerView = new RegisterView();
                registerView.setLocation(locationOnScreen);
                registerView.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point locationOnScreen = FirstView.this.getLocationOnScreen();
                FirstView.this.dispose();
                LoginView loginView = new LoginView();
                loginView.setLocation(locationOnScreen);
                loginView.setVisible(true);
            }
        });
    }
}
