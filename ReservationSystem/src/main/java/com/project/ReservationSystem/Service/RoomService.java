package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Model.User;

import com.project.ReservationSystem.Repository.RoomRepository;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class RoomService implements IRoomService{

    @Override
    public Room addNewRoom(String photoUrl, String roomType, float roomPrice, String address) throws SQLException, IOException {
        return null;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return null;
    }

    @Autowired
    private RoomRepository roomRepository;


    public void add(Room r){
        roomRepository.save(r);
    }
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public List<String> getRoomType(){
        return roomRepository.findDistinctRoomType();
    }

    public List<Room> getAvailableRooms(String type, LocalDate CheckIn, LocalDate CheckOut){
        return roomRepository.findAvailableRoomsByDateAndType(type, CheckIn, CheckOut);
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

    public List<Room> getRoomsByType(String type) {
        return roomRepository.findByRoomType(type);
    }

    public void deleteRoomById(int id) {
        roomRepository.deleteById(id);
    }

    public void update(Room room) {
        roomRepository.save(room);
    }

    public Room GetRoomById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getAvailableRoom(){
        return roomRepository.AvailableRoom();
    }
}
