package com.Hussain.pink.triangle.Utils;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * This will hash the users password when they are registering
 * as a new user or when they are logging in.
 * Created by Hussain on 13/01/2015.
 */
public class HashFunctions {
    private static final Logger LOG = LoggerFactory.getLogger(HashFunctions.class);


    /**
     * Hash the password the user has provided,
     * the algorithm that will be used is SHA-256
     * @param password The plaintext password
     * @return A Hex hash of the password
     */
    public static String hashPassword(char [] password){
        String passwordString = new String(password);//Plaintext password

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256","BC");
            byte [] hashedPassword = messageDigest.digest(passwordString.getBytes());
            return new String(Hex.encodeHex(hashedPassword));//Hex of the hashed password
        }
        catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            LOG.error("There was an error when trying to hash the password that the user has entered",e);
        }
        return null;
    }
}
