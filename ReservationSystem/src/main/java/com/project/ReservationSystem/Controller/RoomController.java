package com.project.ReservationSystem.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Service.RoomService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
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


    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> roomList = roomService.getAllRoom();
        if (roomList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomList);
    }

    @GetMapping("/type")
    public ResponseEntity<List<String>> getRoomType() {
        List<String> roomList = roomService.getAllRoomTypes();
        if (roomList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomList);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Room>> getAvailableRooms(@RequestBody Map<String, String> searchData) {
        String checkin = searchData.get("CheckIn");
        String checkout = searchData.get("CheckOut");
        String type = searchData.get("type");

        // Xử lý dữ liệu và trả về kết quả
        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);

        List<Room> roomList = roomService.getAvailableRoom(checkinDate, checkoutDate, type);
        if (roomList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomList);
    }


//    @PostMapping("/add")
//    public ResponseEntity<Map<String, String>> addRoom(@RequestBody Room room) throws SQLException, IOException {
//        roomService.addNewRoom(room);
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "Add Success");
//        return ResponseEntity.ok(response);
//    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addRoom(
            @RequestParam("price") String price,
            @RequestParam("roomType") String roomType,
            @RequestParam("address") String address,
            @RequestParam("booked") boolean booked,
            @RequestParam("imgdata") MultipartFile imgdata) throws SQLException, IOException {

        // Kiểm tra và lưu file ảnh vào thư mục "image" trong resources

        Room room = new Room();
        room.setPrice(Float.parseFloat(price));
        room.setRoomType(roomType);
        room.setAddress(address);
        room.setBooked(booked);
        String fileName = StringUtils.cleanPath(imgdata.getOriginalFilename());
        room.setImg_url("E:\\Project_Demo\\frontend\\src\\components\\assets\\images\\" + fileName);

        roomService.addNewRoom(room);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Add Success");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search/{type}")
    public ResponseEntity<List<Room>> getRoomsByType(@PathVariable String type) {
        List<Room> roomList = roomService.getRoomsByType(type);

        if (roomList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomList);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable int id) {
        try {
            roomService.deleteRoom(id);
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

            roomService.updateRoom(existingRoom);

            return ResponseEntity.ok("Update Success");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> roomList = roomService.getAvailableRoom();
        if(roomList == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomList);
    }

    @GetMapping("/getroom/{id}")
    public ResponseEntity<Room> getRoomsById(@PathVariable int id) {
        Room room = roomService.getRoomById(id);

        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(room);
    }
}