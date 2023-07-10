package com.example.hotelbooking.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class RoomNotFoundException extends RuntimeException {


   private String message;
   private Date timestamp;

    public RoomNotFoundException(String s) {

        this.message=s;
        this.timestamp=new Date();
    }
}
