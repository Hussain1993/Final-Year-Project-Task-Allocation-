package com.Hussain.pink.triangle.Exception;

/**
 * Created by Hussain on 14/01/2015.
 */
public class UsernameInUseException extends Exception {

    public UsernameInUseException(String message){
        super(message);
    }

    public UsernameInUseException(String message, Throwable cause){
        super(message,cause);
    }
}
