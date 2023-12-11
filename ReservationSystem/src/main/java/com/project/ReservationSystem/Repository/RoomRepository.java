package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("Select distinct RoomType from Room")
    List<String> findDistinctRoomType();
    @Query("Select r from Room r " +
            "where r.RoomType like %:Type% " +
            "and r.id not in " +
            "(select b.room.id from Booking b " +
            "where((b.CheckIn <= :CheckOut) and (b.CheckOut >= :CheckIn)))")
    List<Room> findAvailableRoomsByDateAndType(String Type,LocalDate CheckIn,LocalDate CheckOut);

    Optional<Room> findByAddress(String address);
}
