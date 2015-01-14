package com.Hussain.pink.triangle.Main;

import com.Hussain.pink.triangle.View.FirstView;
import com.Hussain.pink.triangle.View.LogView;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;

/**
 * Created by Hussain on 24/10/2014.
 */
public class MainApp {
    public static void main(String[] args) {
        Security.addProvider(new BouncyCastleProvider());
        LogView.getInstance().setVisible(true);
        new FirstView().setVisible(true);
    }
}