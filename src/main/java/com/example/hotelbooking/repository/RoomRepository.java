package com.example.hotelbooking.repository;


import com.example.hotelbooking.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {


    Optional<Room> findRoomById(Long id);


    @Query("SELECT r FROM Room r WHERE r.capacity >= :requiredCapacity AND r.id NOT IN " +
            "(SELECT b.room.id FROM Booking b WHERE b.startDate <= :endDate AND b.endDate >= :startDate)")
    List<Room> findAvailableRooms(@Param("requiredCapacity") int requiredCapacity,
                                  @Param("startDate") LocalDate startDate,
                                  @Param("endDate") LocalDate endDate);



}
