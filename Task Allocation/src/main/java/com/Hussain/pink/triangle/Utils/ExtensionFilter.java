package com.Hussain.pink.triangle.Utils;

import org.apache.commons.io.FilenameUtils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Extension filter that is used by the JFileChooser
 * either when the user is opening a CSV file that they would like to
 * import into the database or they would like to open or save a TaskAllocation file
 * Created by Hussain on 26/10/2014.
 */
public class ExtensionFilter extends FileFilter {
    private String[] extensions;
    private String description;

    /**
     * Make a new filter with a list of supported extensions
     * @param extensions A list of supported extensions
     * @param description A description for these extensions
     */
    public ExtensionFilter(String [] extensions, String description){
        this.extensions = extensions;
        this.description = description;
    }

    /**
     * Make a new filter with only one extension
     * @param extension The supported extension
     * @param description A description for this extension
     */
    public ExtensionFilter(String extension, String description){
        this(new String [] {extension}, description);
    }


    @Override
    public boolean accept(File f) {
        if(f.isDirectory())
        {
            return true;
        }
        String path = f.getPath();
        return FilenameUtils.isExtension(path,extensions);
    }

    @Override
    public String getDescription() {
        return (description == null ? extensions[0] : description);
    }
}
