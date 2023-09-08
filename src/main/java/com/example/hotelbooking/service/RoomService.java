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


/**
 * Сервис для номеров отеля
 */
@Service
@RequiredArgsConstructor
public class RoomService {


    private final RoomRepository repository;


    /**
     * Создает новую комнату на основе данных из DTO.
     *
     * @param roomDto DTO с данными для создания комнаты.
     * @return Созданная комната.
     */
    public Room createNewRoom(CreateRoomDto roomDto) {

        Room room = new Room();

        room.setArea(roomDto.getArea());
        room.setType(roomDto.getType());
        room.setPrice(roomDto.getPrice());
        room.setCapacity(roomDto.getCapacity());


        return repository.save(room);
    }

    /**
     * Получает список всех номеров.
     *
     * @return Список всех номеров.
     */
    public List<Room> getAllRooms() {
        return repository.findAll();

    }

    /**
     * Получает список свободных номеров.
     *
     * @param roomFilterDto DTO с фильтрами для поиска доступных номеров.
     * @return Список доступных номеров, удовлетворяющих заданным критериям.
     */
    public List<Room> getAvailableRooms(RoomFilterDto roomFilterDto) {


        int capacity = roomFilterDto.getCapacity();
        LocalDate StartDate = LocalDate.parse(roomFilterDto.getStartDate());
        LocalDate EndDate = LocalDate.parse(roomFilterDto.getEndDate());

        List<Room> availableRooms = repository.findAvailableRooms(capacity, StartDate, EndDate);

        return availableRooms;

    }

    /**
     * Получает номер по её уникальному идентификатору.
     *
     * @param id Уникальный идентификатор номера.
     * @return Optional с номером, если он найден, или пустой Optional, если номер не найдена.
     */
    public Optional<Room> getRoomById(Long id) {

        return repository.findRoomById(id);

    }

    /**
     * Редактирует номер на основе предоставленных данных.
     *
     * @param id            Уникальный идентификатор номера.
     * @param createRoomDto DTO с данными для редактирования номера.
     * @return Отредактированный номер
     * @throws RoomNotFoundException если номер с указанным идентификатором не найден.
     */
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

    /**
     * Удаляет номер по её уникальному идентификатору.
     *
     * @param id Уникальный идентификатор номера
     * @return `true`, если номер был найден и успешно удалена; `false`, если номер не найден.
     */
    public boolean deleteRoom(Long id) {

        Optional<Room> room = repository.findRoomById(id);

        if (room.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;


    }


}
