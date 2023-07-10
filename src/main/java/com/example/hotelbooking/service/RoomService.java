package com.example.hotelbooking.service;

import com.example.hotelbooking.dto.CreateRoomDto;
import com.example.hotelbooking.dto.RoomFilterDto;
import com.example.hotelbooking.entity.Room;
import com.example.hotelbooking.exceptions.RoomNotFoundException;
import com.example.hotelbooking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RoomService {


    private final RoomRepository repository;


    public Room createNewRoom(CreateRoomDto roomDto) {

        Room room = new Room();

        room.setArea(roomDto.getArea());
        room.setType(roomDto.getType());
        room.setPrice(roomDto.getPrice());
        room.setCapacity(roomDto.getCapacity());


        return repository.save(room);
    }

    public List<Room> getAllRooms() {
        return repository.findAll();

    }

    public List<Room> getAvailableRooms(RoomFilterDto roomFilterDto) {
        int capacity = roomFilterDto.getCapacity();
        LocalDate StartDate = LocalDate.parse(roomFilterDto.getStartDate());
        LocalDate EndDate = LocalDate.parse(roomFilterDto.getEndDate());

        return null;

    }

    public Optional<Room> getRoomById(Long id) {

        return repository.findRoomById(id);

    }

    public Room editRoom(Long id, CreateRoomDto createRoomDto) throws RoomNotFoundException {
        Optional<Room> roomById = repository.findRoomById(id);

        if (roomById.isPresent()) {
            Room room = roomById.get();

            room.setCapacity(createRoomDto.getCapacity());
            room.setPrice(createRoomDto.getPrice());
            room.setArea(createRoomDto.getArea());
            room.setType(createRoomDto.getType());

            return repository.save(room);

        }

        throw new RoomNotFoundException("Комната с указанным идентификатором не найдена: " + id);

    }

    public boolean deleteRoom(Long id) {

        Optional<Room> room = repository.findRoomById(id);

        if (room.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;


    }


}
