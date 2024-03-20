package com.paololauria.bnb.model.exceptions;
public enum AuthErrorCode {
    EMAIL_EXIST(3),
    WRONG_PASSWORD(4);
    private int code;
    AuthErrorCode(int code){
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
