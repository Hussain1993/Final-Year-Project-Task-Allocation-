package com.Hussain.pink.triangle.Organisation;

import com.Hussain.pink.triangle.Exception.UsernameInUseException;
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
    private static Connection conn;

    public static int assignTasksToEmployees(ArrayList<int []> employeesAndTasks){
        String query = "insert into ASSIGNED_TO values (?,?,?)";
        conn = DatabaseConnection.getDatabaseConnection();
        try{
            stmt = conn.prepareStatement(query);
            for (int[] row : employeesAndTasks) {
                stmt.setInt(1, row[0]);
                stmt.setInt(2, row[1]);
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

    public static int registerNewUser(String username, String hashedPassword) throws UsernameInUseException{
        if(checkIfUsernameExists(username))
        {
            throw new UsernameInUseException("The username is already in use");
        }
        String query = "insert into users(username, password, date_created) values (?,?,?)";
        conn = DatabaseConnection.getDatabaseConnection();
        if(conn != null)
        {
            try{
                stmt = conn.prepareStatement(query);

                stmt.setString(1,username);
                stmt.setString(2,hashedPassword);
                stmt.setTimestamp(3,new Timestamp(System.currentTimeMillis()));

                return stmt.executeUpdate();
            }
            catch (SQLException e) {
                LOG.error("There was an error with the SQL statement", e);
            } finally {
                DbUtils.closeQuietly(conn,stmt,null);
            }
        }
        return 0;
    }


    private static boolean checkIfUsernameExists(String username){
        String query = "select id from users where username = ?";
        conn = DatabaseConnection.getDatabaseConnection();
        ResultSet usernameReturned = null;
        if(conn != null)
        {
            try{
                stmt = conn.prepareStatement(query);

                stmt.setString(1,username);

                usernameReturned = stmt.executeQuery();

                return !isResultSetEmpty(usernameReturned);
            }
            catch (SQLException e) {
                LOG.error("There was an error with the SQL statement", e);
            } finally {
                DbUtils.closeQuietly(conn, stmt, usernameReturned);
            }
        }
        return true;
    }

    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException{
        return !resultSet.first();
    }

    public static boolean logUserIntoSystem(String username, String hashedPassword){
        String query = "select id from users where username = ? and password = ?";
        conn = DatabaseConnection.getDatabaseConnection();
        ResultSet loginDetails = null;
        if(conn != null)
        {
            try{
                stmt = conn.prepareStatement(query);

                stmt.setString(1,username);
                stmt.setString(2,hashedPassword);

                loginDetails = stmt.executeQuery();

                return !isResultSetEmpty(loginDetails);

            }
            catch (SQLException e) {
                LOG.error("There was an error with the SQL statement", e);
            } finally {
                DbUtils.closeQuietly(conn,stmt,loginDetails);
            }
        }
        return false;
    }
}
