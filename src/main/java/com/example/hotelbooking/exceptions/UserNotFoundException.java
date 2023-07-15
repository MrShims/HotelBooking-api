package com.example.hotelbooking.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class UserNotFoundException extends RuntimeException{
    private String message;
    private Date timestamp;

    public UserNotFoundException(String s) {

        this.message=s;
        this.timestamp=new Date();
    }
}
