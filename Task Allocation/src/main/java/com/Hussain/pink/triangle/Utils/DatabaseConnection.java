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

    /**
     * Get a valid connection to the database
     * @return A connection to the Task MatchingAlgorithms system database
     */
    public static Connection getDatabaseConnection(){
        Connection conn;

        if(!DBUtils.getInit())
        {
            try {
                DBUtils.init(TaskAllocationConstants.CLASSNAME_KEY, TaskAllocationConstants.PASSWORD_KEY,
                        TaskAllocationConstants.USERNAME_KEY,TaskAllocationConstants.URL_KEY);
            }
            catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                LOG.error("There was an error with the database connection",e);
                return null;
            }
        }
        try{
            conn = DBUtils.getConnection();
            if(conn != null && conn.isValid(0))//Check that the connection to the database is a valid one
            {
                return conn;
            }
        }
        catch(SQLException e){
            LOG.error("There was an error with the database connection",e);
            return null;
        }
        return null;
    }
}
