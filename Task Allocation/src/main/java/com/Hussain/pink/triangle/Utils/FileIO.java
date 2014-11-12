package com.Hussain.pink.triangle.Utils;

import com.Hussain.pink.triangle.View.ExtensionFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

/**
 * Created by Hussain on 11/11/2014.
 */
public class FileIO {
    public static final int OPEN_MODE = 1;
    public static final int SAVE_MODE = 2;

    public static String openFileDialog(Component component, String[] extensions, String description,int mode){
        String filePath = null;
        int returnValue = 0;
        JFileChooser chooser = new JFileChooser();
        FileFilter filter = new ExtensionFilter(extensions,description);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        switch (mode)
        {
            case OPEN_MODE: returnValue = chooser.showOpenDialog(component);
                break;
            case SAVE_MODE: returnValue = chooser.showSaveDialog(component);
        }
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            filePath = chooser.getSelectedFile().getPath();
        }
        return filePath;
    }

    public static boolean fileExists(String filePath){
        File f = new File(filePath);
        if(f.exists())
        {
            return true;
        }
        return false;

    }
}
