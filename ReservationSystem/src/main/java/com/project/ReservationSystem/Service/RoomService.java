package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService implements IRoomService{

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



    public List<Room> getRoomsByType(String type) {
        return roomRepository.findByRoomType(type);
    }

    public void deleteRoomById(int id) {
        roomRepository.deleteById(id);
    }

    public void update(Room room) {
        roomRepository.save(room);
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

    public Room getRoom(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    @Override
    public List<Room> getAvailableRoom(LocalDate CheckInDate, LocalDate CheckOutDate, String roomType) {
        return roomRepository.findAvailableRoomsByDateAndType(roomType, CheckInDate, CheckOutDate);
    }

    public List<Room> getAvailableRoom(){
        return roomRepository.AvailableRoom();
    }
}
