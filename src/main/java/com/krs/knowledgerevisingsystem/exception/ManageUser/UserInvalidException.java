package com.krs.knowledgerevisingsystem.exception.ManageUser;

public class UserInvalidException extends Exception{
    private static final long serialVersionUID = 1L;
    public UserInvalidException(String message) {
        super(message);
    }
}
