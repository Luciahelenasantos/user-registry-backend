package com.comunidade.api.domain.exceptions;

public class BusinessException extends Exception {
    public BusinessException(final String message){
        super(message);
    }
}