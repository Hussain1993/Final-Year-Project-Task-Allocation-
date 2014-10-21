package com.Hussain.pink.triangle.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hussain on 21/10/2014.
 */
public class PropsGetter {
    private static final Logger LOG = LoggerFactory.getLogger(PropsGetter.class);

    private static PropsGetter instance;
    private static Object instanceLock = new Object();

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

    public void init(String propertiesFilename){
        this.propertiesFilename = propertiesFilename;
        InputStream is = null;
        try{
            is = PropsGetter.class.getClassLoader().getResourceAsStream(this.propertiesFilename);
            properties = new Properties();
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
        String property = null;
        if(properties.containsKey(key))
        {
            property = properties.getProperty(key);
        }
        else
        {
            LOG.error("The properties file {} does not contain the key {}",this.propertiesFilename,key);
            return null;
        }
        return property;
    }

}

