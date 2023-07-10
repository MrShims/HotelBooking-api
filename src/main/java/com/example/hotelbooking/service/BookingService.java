package com.example.hotelbooking.service;


import com.example.hotelbooking.dto.CreateBookingRequest;
import com.example.hotelbooking.entity.Booking;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.entity.User;
import com.example.hotelbooking.exceptions.BookingNotFoundException;
import com.example.hotelbooking.exceptions.CreateBookingException;
import com.example.hotelbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;

    private  final RoomService roomService;

    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();


    }


    public Optional<Booking> getBookingById(Long bookingId) {

        return bookingRepository.findById(bookingId);

    }

    public Booking createBooking(String username, CreateBookingRequest createBookingRequest) {



        Optional<User> byUserName = userService.findByUserName(username);

        Optional<Room> roomById = roomService.getRoomById(createBookingRequest.getId());

        if (byUserName.isEmpty()||roomById.isEmpty())
        {
            throw new CreateBookingException("Пользователь или Номер не найден");
        }


        LocalDate startBookingDate=null;
        LocalDate endBookingDate=null;
        try {
           startBookingDate=LocalDate.parse(createBookingRequest.getStartDate());
             endBookingDate=LocalDate.parse(createBookingRequest.getEndDate());
        } catch (Exception e) {
            throw new CreateBookingException("Неправильный формат даты");
        }


        Booking booking = new Booking();

        booking.setUser(byUserName.get());
        booking.setRoom(roomById.get());
        booking.setStartDate(startBookingDate);
        booking.setEndDate(endBookingDate);
        booking.setStatus("Забронировано");

        return bookingRepository.save(booking);


    }


    public void deleteBookingById(Long id)
    {
        Optional<Booking> byId = bookingRepository.findById(id);

        if (byId.isEmpty()) throw new BookingNotFoundException("Номер с id"+id+" не найден");

        bookingRepository.delete(byId.get());
    }


}
