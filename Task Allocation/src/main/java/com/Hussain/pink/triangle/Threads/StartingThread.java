package com.Hussain.pink.triangle.Threads;

import com.Hussain.pink.triangle.Organisation.DatabaseQueries;
import com.Hussain.pink.triangle.View.LoginView;
import com.Hussain.pink.triangle.View.RegisterView;

/**
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
