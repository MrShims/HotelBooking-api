package com.example.hotelbooking.controller;


import com.example.hotelbooking.dto.CreateBookingRequest;
import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.exceptions.BookingNotFoundException;
import com.example.hotelbooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping
    public ResponseEntity<?> getAllBookings(@RequestParam(value = "startDate", required = false) String startDate) {

        List<Booking> ListBookings = null;

        if (startDate == null) {
            ListBookings = bookingService.getAllBookings();


        } else {
            ListBookings = bookingService.getBookingStartDate(startDate);
        }


        return new ResponseEntity<>(ListBookings, HttpStatus.OK);

    }

    @GetMapping("{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId) {

        Optional<Booking> bookingById = bookingService.getBookingById(bookingId);

        if (bookingById.isPresent()) {
            return new ResponseEntity<>(bookingById.get(), HttpStatus.OK);
        } else throw new BookingNotFoundException("Бронирование с id: " + bookingId + " Не найдено");


    }

    @PostMapping()
    public ResponseEntity<?> createBooking(Principal principal, @Valid @RequestBody CreateBookingRequest createBookingRequest, BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());


            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Booking booking = bookingService.createBooking(principal.getName(), createBookingRequest);


        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PutMapping("{bookingId}")
    public ResponseEntity<?> editBooking(@PathVariable String bookingId) {
        return null;
    }

    @DeleteMapping("{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable Long bookingId) {

        bookingService.deleteBookingById(bookingId);


        return new ResponseEntity<>(HttpStatus.OK);
    }


}
