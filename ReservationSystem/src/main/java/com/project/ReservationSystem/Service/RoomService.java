package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Repository.RoleRepository;
import com.project.ReservationSystem.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RoomService implements IRoomService{

    @Override
    public Room addNewRoom(String photoUrl, String roomType, float roomPrice, String address) throws SQLException, IOException {
        return null;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return null;
    }

    @Override
    public List<Room> getAllRoom() {
        return null;
    }

    @Override
    public String getRoomUrlByRoomId(int roomId) {
        return null;
    }

    @Override
    public void deleteRoom(int roomId) {

    }

    @Override
    public Room updateRoom(int roomId, String roomType, float roomPrice, String photoUrl, String address) {
        return null;
    }

    @Override
    public Optional<Room> getRoomById(int roomId) {
        return Optional.empty();
    }

    @Override
    public List<Room> getAvailableRoom(LocalDate CheckInDate, LocalDate CheckOutDate, String roomType) {
        return null;
    }
}
