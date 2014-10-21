package com.Hussain.pink.triangle.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Hussain on 21/10/2014.
 */
public class PropsGetterTest {
    private static final String PROPERTIES_FILENAME = "test.properties";
    private static final String KEY = "username";
    private static PropsGetter properties;

    @BeforeClass
    public static void init(){
        properties = PropsGetter.getInstance();
        properties.init(PROPERTIES_FILENAME);
    }


    @Test
    public void getPropertyTest(){
        String value = properties.getProperty(KEY);
        assertEquals("Hussain", value);
    }

    @Test
    public void getPropertyNullTest(){
        String value = properties.getProperty("This_key_does_not_exist");
        assertNull(value);
    }

}
