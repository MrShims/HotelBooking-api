package com.example.hotelbooking.service;

import com.example.hotelbooking.dto.CreateRoomDto;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.exceptions.RoomNotFoundException;
import com.example.hotelbooking.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RoomServiceTest {


    private RoomService roomService;

    @Mock
    private RoomRepository repository;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        roomService = new RoomService(repository);

    }

    @Test
    void createNewRoomTest() {

        CreateRoomDto roomDto = new CreateRoomDto();

        roomDto.setType("Ëþêñ");
        roomDto.setCapacity(4);
        roomDto.setArea(25.4);
        roomDto.setPrice(BigDecimal.valueOf(1500));


        Room expectedRoom = new Room();
        expectedRoom.setType("Ëþêñ");
        expectedRoom.setCapacity(4);
        expectedRoom.setArea(25.4);
        expectedRoom.setPrice(BigDecimal.valueOf(1500));

        when(repository.save(any(Room.class))).thenReturn(expectedRoom);
        Room room = roomService.createNewRoom(roomDto);
        verify(repository, times(1)).save(any(Room.class));

        assertEquals(expectedRoom.getType(), room.getType());
        assertEquals(expectedRoom.getCapacity(), room.getCapacity());
        assertEquals(expectedRoom.getArea(), room.getArea());
        assertEquals(expectedRoom.getPrice().intValue(), room.getPrice().intValue());


    }

    @Test
    void getAllRoomsTest() {
        List<Room> expectedRooms = new ArrayList<>();
        expectedRooms.add(new Room());
        expectedRooms.add(new Room());
        when(repository.findAll()).thenReturn(expectedRooms);


        List<Room> actualRooms = roomService.getAllRooms();


        verify(repository, times(1)).findAll();


        assertEquals(expectedRooms, actualRooms);


    }

    @Test
    void getRoomByIdTest() {
        Long roomId = 1L;


        Room expectedRoom = new Room();
        expectedRoom.setId(roomId);
        expectedRoom.setType("Standard");
        expectedRoom.setCapacity(2);

        when(repository.findRoomById(roomId)).thenReturn(Optional.of(expectedRoom));

        Optional<Room> room = roomService.getRoomById(roomId);

        verify(repository, times(1)).findRoomById(roomId);

        assertTrue(room.isPresent());

        assertEquals(expectedRoom.getId(), room.get().getId());
        assertEquals(expectedRoom.getType(), room.get().getType());
        assertEquals(expectedRoom.getCapacity(), room.get().getCapacity());
    }

    @Test
    void editRoomTest() {
        Long roomId = 1L;

        Room existingRoom = new Room();
        existingRoom.setId(roomId);
        existingRoom.setType("Standard");
        existingRoom.setCapacity(2);

        CreateRoomDto newRoomDto = new CreateRoomDto();
        newRoomDto.setType("Deluxe");
        newRoomDto.setCapacity(4);
        newRoomDto.setArea(30.0);
        newRoomDto.setPrice(BigDecimal.valueOf(2000));


        when(repository.findRoomById(roomId)).thenReturn(Optional.of(existingRoom));
        when(repository.save(any(Room.class))).thenAnswer(invocation -> {
            return invocation.<Room>getArgument(0);
        });

        Room editedRoom = roomService.editRoom(roomId, newRoomDto);

        verify(repository, times(1)).findRoomById(roomId);
        assertEquals(roomId, editedRoom.getId());
        assertEquals(newRoomDto.getType(), editedRoom.getType());
        assertEquals(newRoomDto.getCapacity(), editedRoom.getCapacity());
        assertEquals(newRoomDto.getArea(), editedRoom.getArea());
        assertEquals(newRoomDto.getPrice().intValue(), editedRoom.getPrice().intValue());


    }

    @Test
    void editRoomNotFoundTest() {

        Long roomId = 1L;

        when(repository.findRoomById(roomId)).thenReturn(Optional.empty());


        assertThrows(RoomNotFoundException.class, () -> roomService.editRoom(roomId, new CreateRoomDto()));
    }

    @Test
    void deleteRoomTest() {
        Long roomId = 1L;

        Room existingRoom = new Room();
        existingRoom.setId(roomId);

        when(repository.findRoomById(roomId)).thenReturn(Optional.of(existingRoom));

        boolean result = roomService.deleteRoom(roomId);

        verify(repository, times(1)).findRoomById(roomId);
        verify(repository, times(1)).deleteById(roomId);

        assertTrue(result);
    }
}