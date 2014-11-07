package com.Hussain.pink.triangle.Main;

import com.Hussain.pink.triangle.View.LogView;
import com.Hussain.pink.triangle.View.WelcomeScreen;

/**
 * Created by Hussain on 24/10/2014.
 */
public class MainApp {
    public static void main(String[] args) {
        LogView.getInstance().setVisible(true);
        new WelcomeScreen().setVisible(true);
    }
}