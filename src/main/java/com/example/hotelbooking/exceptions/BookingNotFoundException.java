package com.example.hotelbooking.exceptions;

import lombok.Data;

import java.util.Date;

@Data
public class BookingNotFoundException extends RuntimeException{

    public String message;


    public Date timestamp;

    public BookingNotFoundException(String message) {

        this.message=message;
        timestamp=new Date();
    }
}
