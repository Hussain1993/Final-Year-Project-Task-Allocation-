package com.Hussain.pink.triangle.Main;

import com.Hussain.pink.triangle.View.LogView;
import com.Hussain.pink.triangle.View.WelcomeScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hussain on 24/10/2014.
 */
public class MainApp {
    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);
    public static void main(String[] args) {
        LogView.getInstance().setVisible(true);
        new WelcomeScreen().setVisible(true);
    }
}
