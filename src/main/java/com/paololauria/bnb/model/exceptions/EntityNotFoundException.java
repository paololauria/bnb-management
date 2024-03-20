package com.paololauria.bnb.model.exceptions;
public class EntityNotFoundException extends Exception{
    private Class<?> entity;
    public EntityNotFoundException(String message, Class<?> entity){
        super(message);
        this.entity = entity;
    }
    public String getEntityName(){
        return this.entity.getName();
    }
}
