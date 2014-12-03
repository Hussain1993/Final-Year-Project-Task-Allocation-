package com.Hussain.pink.triangle.Utils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

/**
 * Created by Hussain on 11/11/2014.
 * This class is used to show the Open and Save
 * dialog boxes that will be shown to the user
 * when they would like to either open a CSV file or TA
 * file
 * and they will be able to save a TA file from a
 * suggested allocation that is stored within the table
 */
public class FileIO {
    public static final int OPEN_MODE = 1;
    public static final int SAVE_MODE = 2;

    /**
     * Show a JFilechooser to the user, the type of the chooser
     * will depend on the mode that has been passed in
     * @param component The parent Java Swing component of the FileChooser
     * @param extensions The extensions that will be used for the file filter
     * @param description The description for the file extension
     * @param mode Either in the Open or Save mode
     * @return The file path that the user would like to
     * save to or the file path to the file that the user would like
     * to open
     */
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

    /**
     * Small utility method to check if a file exists
     * using a String path
     * @param filePath The file path to check if it exists
     *                 where this is a String object
     * @return true if the file exists, false otherwise
     */
    public static boolean fileExists(String filePath){
        if(filePath == null)
        {
            return false;
        }
        File f = new File(filePath);
        if(f.exists())
        {
            return true;
        }
        return false;

    }
}
