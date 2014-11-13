package com.Hussain.pink.triangle.Organisation;

import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Hussain on 12/11/2014.
 */
public class DatabaseQueries {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseQueries.class);

    private static PreparedStatement stmt;

    public static int assignTasksToEmployees(ArrayList<int []> employeesAndTasks){
        Connection conn;
        PreparedStatement stmt = null;
        String query = "insert into ASSIGNED_TO values (?,?,?)";
        conn = DatabaseConnection.getDatabaseConnection();
        try{
            stmt = conn.prepareStatement(query);
            for (int i = 0; i < employeesAndTasks.size(); i++) {
                int [] row = employeesAndTasks.get(i);
                stmt.setInt(1,row[0]);
                stmt.setInt(2,row[1]);
                stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                stmt.addBatch();
            }
            return stmt.executeBatch().length;
        }
        catch(SQLException e){
            LOG.error("There was an error with the SQL statement",e);
        }
        finally {
            DbUtils.closeQuietly(conn,stmt,null);
        }
        return 0;
    }
}
