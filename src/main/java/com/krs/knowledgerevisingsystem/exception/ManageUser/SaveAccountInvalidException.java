package com.krs.knowledgerevisingsystem.exception.ManageUser;

public class SaveAccountInvalidException extends Exception{
    private static final long serialVersionUID = 1L;
    public SaveAccountInvalidException(String message) {
        super(message);
    }
}
