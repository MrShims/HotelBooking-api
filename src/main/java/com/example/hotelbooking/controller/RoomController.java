package com.example.hotelbooking.controller;


import com.example.hotelbooking.dto.CreateRoomDto;
import com.example.hotelbooking.dto.RoomFilterDto;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для обратки запросов по номерам отеля
 */
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {


    private final RoomService roomService;

    /**
     * Получает список комнат в зависимости от фильтрации.
     * @param roomFilterDto Объект, содержащий параметры фильтрации комнат (необязательный параметр).
     * @return ResponseEntity с списком комнат в соответствии с фильтрацией или всеми комнатами, если фильтр не указан.
     */
    @GetMapping
    public ResponseEntity<?> getRooms(@ModelAttribute RoomFilterDto roomFilterDto) {
        if (roomFilterDto.getStartDate() == null) {
            List<Room> allRooms = roomService.getAllRooms();
            return new ResponseEntity<>(allRooms, HttpStatus.OK);

        } else {
            List<Room> availableRooms = roomService.getAvailableRooms(roomFilterDto);
            return new ResponseEntity<>(availableRooms, HttpStatus.OK);
        }

    }


    /**
     * Получает информацию о комнате по ее уникальному идентификатору.
     * @param roomId Уникальный идентификатор комнаты, которую нужно получить.
     * @return ResponseEntity с информацией о комнате или HTTP-статусом BAD_REQUEST (400) в случае отсутствия комнаты.
     */
    @GetMapping({"{roomId}"})
    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
        Optional<Room> roomById = roomService.getRoomById(roomId);

        if (roomById.isPresent()) {
            return new ResponseEntity<>(roomById.get(), HttpStatus.OK);

        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    /**
     * Создает новую комнату на основе предоставленных данных.
     * @param createRoomDto Объект с данными для создания новой комнаты.
     * @return ResponseEntity с информацией о созданной комнате или сообщением об ошибке, если пользователь не имеет прав на создание.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomDto createRoomDto) {
        Room newRoom = roomService.createNewRoom(createRoomDto);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    /**
     * Изменяет данные о существующей комнате на основе предоставленных данных.
     * @param roomId Уникальный идентификатор комнаты, которую нужно изменить.
     * @param createRoomDto createRoomDto Объект с данными для изменения комнаты.
     * @return ResponseEntity с информацией об измененной комнате.
     */
    @PutMapping("{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> editRoom(@PathVariable Long roomId, @RequestBody CreateRoomDto createRoomDto) {

        Room room = roomService.editRoom(roomId, createRoomDto);

        return new ResponseEntity<>(room, HttpStatus.OK);


    }

    /**
     * Удаляет комнату по ее уникальному идентификатору.
     * @param roomId Уникальный идентификатор номера, который нужно удалить.
     * @return ResponseEntity с HTTP-статусом OK (200) в случае успешного удаления или
     *  * HTTP-статусом NOT_FOUND (404) в случае отсутствия комнаты с указанным идентификатором.
     */
    @DeleteMapping("{roomId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {

        boolean b = roomService.deleteRoom(roomId);

        if (b) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
