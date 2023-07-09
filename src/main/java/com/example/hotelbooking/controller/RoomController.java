package com.example.hotelbooking.controller;


import com.example.hotelbooking.dto.CreateRoomDto;
import com.example.hotelbooking.dto.RoomFilterDto;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.exceptions.RoomNotFoundException;
import com.example.hotelbooking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {


    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<?> getRooms(@ModelAttribute RoomFilterDto roomFilterDto) {
        if (roomFilterDto.getStartDate() == null) {
            List<Room> allRooms = roomService.getAllRooms();
            return new ResponseEntity<>(allRooms, HttpStatus.OK);

        }
        else
        {
                List<Room> availableRooms = roomService.getAvailableRooms(roomFilterDto);
                 return new ResponseEntity<>(availableRooms,HttpStatus.OK);
        }

    }



    @GetMapping({"{roomId}"})
    public ResponseEntity<?> getRoomById(@PathVariable Long roomId) {
        Optional<Room> roomById = roomService.getRoomById(roomId);

        if (roomById.isPresent()) {
            return new ResponseEntity<>(roomById.get(), HttpStatus.OK);

        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody CreateRoomDto createRoomDto) {
        Room newRoom = roomService.createNewRoom(createRoomDto);
        return new ResponseEntity<>(newRoom, HttpStatus.CREATED);
    }

    @PutMapping("{roomId}")
    public ResponseEntity<?> editRoom(@PathVariable Long roomId, @RequestBody CreateRoomDto createRoomDto) throws RoomNotFoundException {

        Room room = roomService.editRoom(roomId, createRoomDto);

        return new ResponseEntity<>(room, HttpStatus.OK);


    }

    @DeleteMapping("{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long roomId) {

        boolean b = roomService.deleteRoom(roomId);

        if (b)
        {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
