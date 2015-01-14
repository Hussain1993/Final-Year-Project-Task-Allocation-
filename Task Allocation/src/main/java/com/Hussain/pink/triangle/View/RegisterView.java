package com.Hussain.pink.triangle.View;

import com.Hussain.pink.triangle.Exception.UsernameInUseException;
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
public class RegisterView extends JFrame{
    private static final Logger LOG = LoggerFactory.getLogger(RegisterView.class);

    private JPanel root;
    private JTextField usernameText;
    private JPasswordField passwordText;
    private JPasswordField passwordText2;
    private JButton registerButton;
    private JButton loginScreenButton;
    private JButton backButton;

    public RegisterView(){
        super("Register New User");
        setContentPane(root);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addActionListeners();

        pack();
    }

    private void addActionListeners(){
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                char [] password1 = passwordText.getPassword();
                char [] password2 = passwordText2.getPassword();
                if(!username.isEmpty() && password1.length > 0 && password2.length > 0)
                {
                    if(passwordsMatch())
                    {
                        String hashedPassword = HashFunctions.hashPassword(password1);
                        try{
                            int numberOfUsersEnteredIntoDatabase = DatabaseQueries.registerNewUser(username,hashedPassword);
                            if(numberOfUsersEnteredIntoDatabase == 1)
                            {
                                LOG.info("A new user has been added to the database, with the username: {}",username);
                                RegisterView.this.dispose();
                                new LoginView().setVisible(true);
                            }
                            else
                            {
                                LOG.error("There was error when trying to insert the new user into the database," +
                                        "where more than one user has been added");
                            }
                        }
                        catch (UsernameInUseException usernameInUseException){
                            JOptionPane.showMessageDialog(new JFrame(),"Please choose another username","Warning",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(new JFrame(),"The passwords that you have entered " +
                                "do not match","Warning",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(),"One or more required fields are empty","Warning",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterView.this.dispose();
                new LoginView().setVisible(true);
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegisterView.this.dispose();
                new FirstView().setVisible(true);
            }
        });
    }

    private boolean passwordsMatch(){
        String password1 = new String(passwordText.getPassword());
        String password2 = new String(passwordText2.getPassword());

        return password1.equals(password2);
    }
}
