package com.Hussain.pink.triangle.Organisation;

import com.Hussain.pink.triangle.Exception.UsernameInUseException;
import com.Hussain.pink.triangle.Utils.DatabaseConnection;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

/**
 * A class where all the database queries within the application
 * will be executed, provides a common place for all queries
 *
 * Created by Hussain on 12/11/2014.
 */
public class DatabaseQueries {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseQueries.class);

    private static PreparedStatement stmt;
    private static Connection conn;

    /**
     * This method will take the tasks the user has chosen to assign to the employees
     * in the task allocation and add this allocation to the database.
     * @param employeesAndTasks A list of employee IDs and the task ID they have been assigned to
     * @return The number of rows that have been inserted into the database
     */
    public static int assignTasksToEmployees(ArrayList<int []> employeesAndTasks){
        String query = "insert into ASSIGNED_TO values (?,?,?)";
        conn = DatabaseConnection.getDatabaseConnection();
        try{
            stmt = conn.prepareStatement(query);
            for (int[] row : employeesAndTasks) {
                stmt.setInt(1, row[0]);//This is the employee ID
                stmt.setInt(2, row[1]);//This is the task ID
                stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));//This is the date that the employee has been assigned the task
                stmt.addBatch();//Add this query to a batch of queries and only execute the batch once we have finished
                //processing over the list, this makes the insert query faster.
            }
            return stmt.executeBatch().length; //The number of rows that have been inserted
        }
        catch(SQLException e){
            LOG.error("There was an error with the SQL statement",e);
            return 0;
        }
        finally {
            DbUtils.closeQuietly(conn,stmt,null);
        }
    }

    /**
     * Register a new user into the database
     * @param username The username the user has chosen to use
     * @param hashedPassword A hash of the password the user has entered
     * @return The number of rows that have been inserted into the database, this should always be 1
     * @throws UsernameInUseException If the username the user has chosen already exists within the system
     */
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


    /**
     * Method to check if the username is already in use
     * @param username The username the user has chosen
     * @return True if the username is already in use, false otherwise
     */
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
                return true;
            } finally {
                DbUtils.closeQuietly(conn, stmt, usernameReturned);
            }
        }
        return true;
    }

    /**
     * Method to check if the result set returned from the SQL query is empty
     * @param resultSet The result set to check
     * @return True if the result set is empty, false otherwise
     * @throws SQLException If there is an error using the result set, the result set
     * might have been closed
     */
    private static boolean isResultSetEmpty(ResultSet resultSet) throws SQLException{
        return !resultSet.first();
    }

    /**
     * Log a user into the system
     * @param username The username the user has entered
     * @param hashedPassword A hash of the password the user has entered
     * @return True if the user is able to login, ie there is match with a username and password,
     * false otherwise
     */
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
                return false;
            } finally {
                DbUtils.closeQuietly(conn,stmt,loginDetails);
            }
        }
        return false;
    }
}
