package com.ecommerceSolutions.Exceptions;

public class UserDoesNotExistWithEmail extends Exception{
    public UserDoesNotExistWithEmail(String message) {
        super("User Does Not Exist With Email : " + message);
    }
}
