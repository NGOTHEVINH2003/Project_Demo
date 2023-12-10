package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoomService implements IUserService{

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
    public User registerUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public void deleteUser(String email) {

    }

    @Override
    public User findUserByEmail(String email) {
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

    public Room getRoomById(int id) {
        return roomRepository.findById(id).orElse(null);
    }

    public List<Room> getAvailableRoom(){
        return roomRepository.AvailableRoom();
    }
}
