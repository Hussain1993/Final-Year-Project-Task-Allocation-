package com.Hussain.pink.triangle.Main;

import com.Hussain.pink.triangle.View.FirstView;
import com.Hussain.pink.triangle.View.LogView;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * This is where it all begins
 * Created by Hussain on 24/10/2014.
 */
public class MainApp {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());//Add the Bouncy Castle security provider at the start
        LogView.getInstance().setVisible(true);//Show the logging window
        new FirstView().setVisible(true);//Show the actual application to the user
    }
}