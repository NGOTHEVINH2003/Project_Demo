package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IRoomService{

    @Autowired
    private RoomRepository roomRepository;


    public List<Room> getRoomsByType(String type) {
        return roomRepository.findByRoomType(type);
    }



    @Override
    public void addNewRoom(Room room) throws SQLException, IOException {
        roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomType();
    }

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public String getRoomUrlByRoomId(int roomId) {
        return null;
    }

    @Override
    public void deleteRoom(int roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public void updateRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room getRoomById(int roomId) {
        return roomRepository.findById(roomId).orElse(null);
    }

    @Override
    public List<Room> getAvailableRoom(LocalDate CheckInDate, LocalDate CheckOutDate, String roomType) {
        return roomRepository.findAvailableRoomsByDateAndType(roomType, CheckInDate, CheckOutDate);
    }

    public List<Room> getAvailableRoom(){
        return roomRepository.AvailableRoom();
    }

    public List<Room> getSortedRoomList(int floor) {
        return roomRepository.sortByRoomId(floor);
    }

    public boolean isRoomIdExists(Integer roomId) {
        return roomRepository.existsByRoomId(roomId);
    }
}
