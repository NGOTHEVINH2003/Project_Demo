package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Room;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    void addNewRoom(Room room) throws SQLException, IOException;

    List<String> getAllRoomTypes();
    List<Room> getAllRoom();
    String getRoomUrlByRoomId(int roomId);
    void deleteRoom(int roomId);
    void updateRoom(Room room);

    Room getRoomById(int roomId);

    List<Room> getAvailableRoom(LocalDate CheckInDate, LocalDate CheckOutDate, String roomType);
}
