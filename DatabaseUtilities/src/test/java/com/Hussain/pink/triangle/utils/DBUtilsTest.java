package com.Hussain.pink.triangle.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Hussain on 21/10/2014.
 */
public class DBUtilsTest {
    private static final String PROPERTIES_FILENAME = "database.properties";
    private static final String CLASSNAME_KEY = "classname";
    private static final String PASSWORD_KEY = "password";
    private static final String USERNAME_KEY = "username";
    private static final String URL_KEY = "url";

    @BeforeClass
    public static void init(){
        try{
            DBUtils.init(PROPERTIES_FILENAME, CLASSNAME_KEY, PASSWORD_KEY, USERNAME_KEY, URL_KEY);
        }
        catch(InstantiationException instantiationException){
            fail(instantiationException.getMessage());
        }
        catch (IllegalAccessException iaException) {
            fail(iaException.getMessage());
        }
        catch (ClassNotFoundException cnfException) {
            fail(cnfException.getMessage());
        }
    }

    @Test
    public void getClassNameTest(){
        assertEquals(DBUtils.getClassName(), "com.mysql.jdbc.Driver");
    }

    @Test
    public void getPasswordTest(){
        assertEquals(DBUtils.getPassword(), "password");
    }

    @Test
    public void getUsernameTest(){
        assertEquals(DBUtils.getUsername(), "username");
    }

    @Test
    public void getURLTest(){
        assertEquals(DBUtils.getUrl(), "jdbc:mysql://localhost:3306/testDatabase");
    }
}
