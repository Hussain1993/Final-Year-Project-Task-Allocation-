package com.Hussain.pink.triangle.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Hussain on 21/10/2014.
 */
public class DBUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DBUtils.class);

    private static String className;
    private static String password;
    private static String username;
    private static String url;

    private static boolean init = false;

    private DBUtils(){}

    public static void init(String propertiesFilename, String classNameKey, String passwordKey
            ,String usernameKey, String urlKey) throws InstantiationException, IllegalAccessException, ClassNotFoundException{
        PropsGetter properties = PropsGetter.getInstance();
        properties.init(propertiesFilename);

        className = properties.getProperty(classNameKey);
        password = properties.getProperty(passwordKey);
        username = properties.getProperty(usernameKey);
        url = properties.getProperty(urlKey);

        Class.forName(className).newInstance();

        init = true;
    }

    public static Connection getConnection() throws SQLException{
        if(username != null && password != null && url != null)
        {
            LOG.info("A connection to the database has been established");
            return DriverManager.getConnection(url, username, password);
        }
        throw new SQLException("There was an error when trying to get a connection to the database");
    }

    public static final String getClassName() {
        return className;
    }

    public static final String getPassword() {
        return password;
    }

    public static final String getUsername() {
        return username;
    }

    public static final String getUrl() {
        return url;
    }

    public static boolean getInit(){
        return init;
    }

}
