package com.Hussain.pink.triangle.Threads;

import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.View.LoginView;
import com.Hussain.pink.triangle.View.RegisterView;

/**
 * This is the starting thread of the application, it will check
 * if there are any users within the system,
 * if there are users within the system then the application will show the login screen to the user
 * but on other hand if there aren't any users within the database then the application will show the
 * register view to the user so they cn create a new username and password and start using the system
 *
 * Created by Hussain on 09/02/2015.
 */
public class StartingThread implements Runnable {


    @Override
    public void run() {
        if(DatabaseQueries.areThereUsersInTheSystem())
        {
            new LoginView().setVisible(true);
        }
        else
        {
            new RegisterView().setVisible(true);
        }
    }
}
