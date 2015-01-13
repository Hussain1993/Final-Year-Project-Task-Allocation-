package com.Hussain.pink.triangle.Utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import java.security.Security;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hussain on 13/01/2015.
 */
public class HashFunctionsTest {


    @Test
    public void testHashPassword() {
        Security.addProvider(new BouncyCastleProvider());
        String hash =  "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";
        char [] password = {'p','a','s','s','w','o','r','d'};
        assertEquals(HashFunctions.hashPassword(password),hash);
    }
}
