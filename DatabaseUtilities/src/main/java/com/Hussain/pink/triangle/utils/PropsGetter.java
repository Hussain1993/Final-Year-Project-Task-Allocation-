package com.Hussain.pink.triangle.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * Created by Hussain on 21/10/2014.
 */
public class PropsGetter {
    private static final Logger LOG = LoggerFactory.getLogger(PropsGetter.class);
    private static final String CONFIG_FILE_PATH_PROPERTY_KEY="task.allocation.properties.file";

    private static PropsGetter instance;
    private static final Object instanceLock = new Object();

    private String propertiesFilename;
    private Properties properties;

    private PropsGetter(){
        super();
    }

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

    public void init(){
        File propertiesFile = new File(System.getProperty(CONFIG_FILE_PATH_PROPERTY_KEY));
        InputStream is = null;
        try {
            is = new FileInputStream(propertiesFile);
            properties = new Properties();
            properties.load(is);
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

    public void init(String propertiesFilename){
        this.propertiesFilename = propertiesFilename;
        InputStream is = null;
        try{
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

