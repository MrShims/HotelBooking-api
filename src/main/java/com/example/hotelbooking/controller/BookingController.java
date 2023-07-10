package com.example.hotelbooking.controller;


import com.example.hotelbooking.dto.CreateBookingRequest;
import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.exceptions.BookingNotFoundException;
import com.example.hotelbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;


    @GetMapping
    public ResponseEntity<?> getAllBookings() {
        List<Booking> allBookings = bookingService.getAllBookings();

        return new ResponseEntity<>(allBookings, HttpStatus.OK);

    }

    @GetMapping("{bookingId}")
    public ResponseEntity<?> getAllBookingById(@PathVariable Long bookingId) {

        Optional<Booking> bookingById = bookingService.getBookingById(bookingId);

        if (bookingById.isPresent()) {
            return new ResponseEntity<>(bookingById.get(), HttpStatus.OK);
        } else throw new BookingNotFoundException("Бронирование с id: " + bookingId + " Не найдено");


    }

    @PostMapping()
    public ResponseEntity<?> createBooking(Principal principal, @RequestBody CreateBookingRequest createBookingRequest) {


        Booking booking = bookingService.createBooking(principal.getName(), createBookingRequest);


        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PutMapping("{bookingId}")
    public ResponseEntity<?> editBooking(@PathVariable String bookingId) {
        return null;
    }

    @DeleteMapping("{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long bookingId)
    {

        bookingService.deleteBookingById(bookingId);


        return new ResponseEntity<>(HttpStatus.OK);
    }


}
