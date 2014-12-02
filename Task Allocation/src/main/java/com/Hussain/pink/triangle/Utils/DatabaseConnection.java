package com.Hussain.pink.triangle.Utils;

import com.Hussain.pink.triangle.Model.TaskAllocationConstants;
import com.Hussain.pink.triangle.utils.DBUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Hussain on 12/11/2014.
 *
 * Utilities class used to get a connection to the database
 */
public class DatabaseConnection {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConnection.class);

    public static Connection getDatabaseConnection(){
        Connection conn;

        if(!DBUtils.getInit())
        {
            try {
                DBUtils.init(TaskAllocationConstants.PROPERTIES_FILENAME,TaskAllocationConstants.CLASSNAME_KEY, TaskAllocationConstants.PASSWORD_KEY, TaskAllocationConstants.USERNAME_KEY,TaskAllocationConstants.URL_KEY);
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                LOG.error("There was an error with the database connection",e);
            }
        }
        try{
            conn = DBUtils.getConnection();
            if(conn != null && conn.isValid(0))
            {
                return conn;
            }
        }
        catch(SQLException e){
            LOG.error("There was an error with the database connection",e);
        }
        return null;
    }
}
