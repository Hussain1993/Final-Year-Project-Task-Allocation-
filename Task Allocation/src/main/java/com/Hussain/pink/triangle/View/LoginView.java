package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.Utils.HashFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Hussain on 24/12/2014.
 */
public class LoginView extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(LoginView.class);

    private JPanel root;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton registerScreenButton;
    private JButton backButton;
    private JLabel statusLabel;


    public LoginView(){
        super("Login");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(root);
        addActionListeners();
        pack();
    }


    private void addActionListeners(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                char [] password = passwordText.getPassword();

                if(!username.isEmpty() && password.length > 0)
                {
                    String hashedPassword = HashFunctions.hashPassword(password);
                    if(DatabaseQueries.logUserIntoSystem(username,hashedPassword))
                    {
                        LOG.info("The user {} has logged into the system", username);
                        LoginView.this.dispose();
                        new WelcomeScreen().setVisible(true);
                    }
                    else
                    {
                        LOG.error("The user has failed to login with the username {}", username);
                        statusLabel.setText("Try Again");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(),"One or more required " +
                            "fields are empty","Warning",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView.this.dispose();
                new RegisterView().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginView.this.dispose();
                new FirstView().setVisible(true);
            }
        });
    }
}
