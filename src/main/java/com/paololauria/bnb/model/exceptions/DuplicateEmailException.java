package com.paololauria.bnb.model.exceptions;
public class DuplicateEmailException extends Exception{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
