package com.project.ReservationSystem.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Service.RoomService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {

    @Autowired
    private RoomService roomService;


    @GetMapping
    public List<Room> getAllRooms() {
        List<Room> roomList =  roomService.getAllRooms();
        return roomList;
    }

    @GetMapping("/type")
    public List<String> getRoomType(){
        List<String> roomList =  roomService.getRoomType();
        return roomList;
    }

    @PostMapping("/search")
    public List<Room> getAvailableRooms(@RequestBody Map<String, String> searchData) {
        String checkin = searchData.get("CheckIn");
        String checkout = searchData.get("CheckOut");
        String type = searchData.get("type");

        // Xử lý dữ liệu và trả về kết quả
        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);

        List<Room> roomList =  roomService.getAvailableRooms(type, checkinDate, checkoutDate);
        return roomList;
    }


    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addRoom(@RequestBody Room room) {
        roomService.add(room);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Add Success");
        return ResponseEntity.ok(response);
    }



    @GetMapping("/search/{type}")
    public List<Room> getRoomsByType(@PathVariable String type) {

        return roomService.getRoomsByType(type);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable int id) {
        try {
            roomService.deleteRoomById(id);
            return ResponseEntity.ok("Room deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting room");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRoom(@PathVariable int id, @RequestBody Room updatedRoom) {
        Room existingRoom = roomService.getRoomById(id);

        if (existingRoom != null) {
            existingRoom.setPrice(updatedRoom.getPrice());
            existingRoom.setRoomType(updatedRoom.getRoomType());
            existingRoom.setAddress(updatedRoom.getAddress());
            existingRoom.setImg_url(updatedRoom.getImg_url());
            existingRoom.setBooked(updatedRoom.isBooked());

            roomService.update(existingRoom);

            return ResponseEntity.ok("Update Success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/available")
    public List<Room> getAvailableRooms() {
        List<Room> roomList =  roomService.getAvailableRoom();
        return roomList;
    }
}


