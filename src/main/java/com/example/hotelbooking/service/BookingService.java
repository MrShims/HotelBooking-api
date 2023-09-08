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

/**
 * Сервис для управлении бронировании номеров
 */
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;

    private final RoomService roomService;

    /**
     * Получает список всех бронирований.
     *
     * @return Список всех бронирований в системе.
     */
    public List<Booking> getAllBookings() {

        return bookingRepository.findAll();


    }


    /**
     * Получает бронирование по его уникальному идентификатору.
     *
     * @param bookingId Уникальный идентификатор бронирования.
     * @return Optional с бронированием, если оно найдено, или пустой Optional, если бронирование не найдено.
     */
    public Optional<Booking> getBookingById(Long bookingId) {

        return bookingRepository.findById(bookingId);

    }

    /**
     * Создает новое бронирование на основе предоставленных данных.
     *
     * @param username             Имя пользователя на которое бронируется номер.
     * @param createBookingRequest Объект с данными об индефикаторе комнаты, начальной и конечной даты бронирования.
     * @return Созданное бронирование.
     * @throws CreateBookingException если пользователь или номер не найдены.
     */
    public Booking createBooking(String username, CreateBookingRequest createBookingRequest) {


        Optional<User> byUserName = userService.findByUserName(username);

        Optional<Room> roomById = roomService.getRoomById(createBookingRequest.getId());

        if (byUserName.isEmpty() || roomById.isEmpty()) {
            throw new CreateBookingException("Пользователь или Номер не найден");
        }

        LocalDate startBookingDate = LocalDate.parse(createBookingRequest.getStartDate());
        LocalDate endBookingDate = LocalDate.parse(createBookingRequest.getEndDate());


        Booking booking = new Booking();

        booking.setUser(byUserName.get());
        booking.setRoom(roomById.get());
        booking.setStartDate(startBookingDate);
        booking.setEndDate(endBookingDate);
        booking.setStatus("Забронировано");

        return bookingRepository.save(booking);


    }

    /**
     * Получает список бронирований, начинающихся после указанной даты.
     *
     * @param startDate Начальная дата для фильтрации бронирований
     * @return Список бронирований, начинающихся после указанной даты.
     */
    public List<Booking> getBookingStartDate(String startDate) {
        LocalDate localDate = LocalDate.parse(startDate);

        List<Booking> allByStartDateAfter = bookingRepository.findAllByStartDateAfter(localDate);

        return allByStartDateAfter;
    }


    /**
     * Удаляет бронирование по его уникальному идентификатору.
     *
     * @param id Уникальный идентификатор бронирования.
     * @throws BookingNotFoundException если бронирование с указанным идентификатором не найдено.
     */
    public void deleteBookingById(Long id) {
        Optional<Booking> byId = bookingRepository.findById(id);

        if (byId.isEmpty()) throw new BookingNotFoundException("Номер с id" + id + " не найден");

        bookingRepository.delete(byId.get());
    }


}
