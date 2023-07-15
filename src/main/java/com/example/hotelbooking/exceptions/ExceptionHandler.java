package com.example.hotelbooking.exceptions;


import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import io.jsonwebtoken.SignatureException;


@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<String> handleRoomNotFoundException(RoomNotFoundException ex) {


        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BookingNotFoundException.class)
    public ResponseEntity<String> handleBookingNotFoundException(BookingNotFoundException ex) {


        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }

    @org.springframework.web.bind.annotation.ExceptionHandler(JwtException.class)
    public ResponseEntity<?> handleTokenException(JwtException ex) {


        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }


    @org.springframework.web.bind.annotation.ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {


        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }




}
