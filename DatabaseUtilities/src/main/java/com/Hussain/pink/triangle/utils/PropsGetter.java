package com.Hussain.pink.triangle.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by Hussain on 21/10/2014.
 *
 * This class should be used to load a properties
 * file to be used at runtime. This class gives two options
 * when loading the properties file, either the file is
 * bundled in with the JAR and it is on the classpath,
 * or on the other hand the user will be able to the pass the
 * path of the properties file via the command line using a
 * custom system property by using the -D option when running
 * this application
 */
public class PropsGetter {
    private static final Logger LOG = LoggerFactory.getLogger(PropsGetter.class);
    private static final String CONFIG_FILE_PATH_PROPERTY_KEY="task.allocation.properties.file";

    private static PropsGetter instance;
    private static final Object instanceLock = new Object();

    private String propertiesFilename;
    private Properties properties;

    /**
     * Private constructor as I do not want anyone to
     * create an instance of this object, but by using the
     * methods that I have provided
     */
    private PropsGetter(){
        super();
    }


    /**
     * This method will return an instance of this object
     * if one does not already exist then it will create a new one
     * but if there is already one available then this one will be returned
     * @return An instance of this class
     */
    public static final PropsGetter getInstance(){
        if(instance == null)
        {
            synchronized (instanceLock) {
                if(instance == null)
                {
                    instance = new PropsGetter();
                }
                instanceLock.notifyAll();
            }
        }
        return instance;
    }

    /**
     * This is loading the properties file into
     * memory using the custom system property defined
     */
    public void init(){
        //Read the system property that was specified in the command line option
        File propertiesFile = new File(System.getProperty(CONFIG_FILE_PATH_PROPERTY_KEY));
        InputStream is = null;
        try {
            is = new FileInputStream(propertiesFile);
            properties = new Properties();
            properties.load(is);
            this.propertiesFilename = propertiesFile.getName();
        }
        catch (FileNotFoundException e) {
            LOG.error("The properties file was not found",e);
        }
        catch (IOException e){
            LOG.error("There was an error while loading the properties file into memory",e);
        }
        finally {
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * This is loading the properties file
     * when it is included in the classpath of the
     * application
     * @param propertiesFilename The name of the properties file
     *                           to look for on the classpath
     */
    public void init(String propertiesFilename){
        this.propertiesFilename = propertiesFilename;
        InputStream is = null;
        try{
            //Load the resource from the classpath
            is = PropsGetter.class.getClassLoader().getResourceAsStream(this.propertiesFilename);
            properties = new Properties();
            if(is == null)
            {
                throw new FileNotFoundException("The properties file was not found");
            }
            properties.load(is);
        }
        catch(FileNotFoundException fnfException){
            LOG.error("The properties files {} was not found on the classpath",this.propertiesFilename,fnfException);
        }
        catch(IOException ioException){
            LOG.error("There was an error when trying to load the properties file {}",this.propertiesFilename,ioException);
        }
        finally{
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * This method should be used to query the
     * properties file that has been loaded into memory
     * using a key for the property that you wish to retrieve
     * @param key The key for the property
     * @return A String of the returned property from the file,
     * null otherwise
     */
    public String getProperty(String key){
        if(properties.containsKey(key))
        {
            return properties.getProperty(key);
        }
        else
        {
            LOG.error("The properties file {} does not contain the key {}",this.propertiesFilename,key);
            return null;
        }
    }

}

