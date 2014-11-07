package com.Hussain.pink.triangle.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    /**
     * This method will initialise the Database connect class,
     * where it will load the properties file specified and
     * read the properties that are required to connect to the database
     * @param propertiesFilename The name of the properties file that
     *                           contains all the properties used to connect to the
     *                           database
     * @param classNameKey The Driver class name that should be used to connect to the
     *                     database
     * @param passwordKey Password used to connect to the database
     * @param usernameKey Username used to connect to the database
     * @param urlKey The URL of the database
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
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

    /**
     * Get a connection to the database using the
     * properties specified.
     * @return A connection to the database, otherwise
     * throw an SQLException
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException{
        Connection conn;
        if(username != null && password != null && url != null)
        {
            conn = DriverManager.getConnection(getUrl(),getUsername(),getPassword());
            if(conn != null && conn.isValid(0))
            {
                LOG.info("A connection to the database has been established");
                return conn;
            }
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
