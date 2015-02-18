package com.Hussain.pink.triangle.Exception;

/**
 * Created by Hussain on 18/02/2015.
 */
public class ProjectGroupNotFoundException extends Exception {

    public ProjectGroupNotFoundException(String message){
        super(message);
    }

    public ProjectGroupNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
