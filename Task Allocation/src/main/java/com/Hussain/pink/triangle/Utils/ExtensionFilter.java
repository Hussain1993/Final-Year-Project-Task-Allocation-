package com.Hussain.pink.triangle.Utils;

import org.apache.commons.io.FilenameUtils;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created by Hussain on 26/10/2014.
 */
public class ExtensionFilter extends FileFilter {
    private String[] extensions;
    private String description;

    public ExtensionFilter(String [] extensions, String description){
        this.extensions = extensions;
        this.description = description;
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
