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
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    @GetMapping("/search")
    public ResponseEntity<List<Room>> getAvailableRooms(
            @RequestParam("checkInDate") String checkInDate,
            @RequestParam("checkOutDate") String checkOutDate,
            @RequestParam("roomType") String roomType) {


        LocalDate checkinDate = LocalDate.parse(checkInDate);
        LocalDate checkoutDate = LocalDate.parse(checkOutDate);

        List<Room> roomList = roomService.getAvailableRoom(checkinDate, checkoutDate, roomType);
        if (roomList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomList);
    }


    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addRoom(
            @RequestParam(name = "price") String price,
            @RequestParam(name = "roomType") String roomType,
            @RequestParam(name = "photo") MultipartFile imgdata,
            @RequestParam(name = "roomId") String roomId,
            @RequestParam(name = "floor") String floor,
            @RequestParam(name = "information") String information
    ) throws SQLException, IOException {

        try {
            String uploadDir = "E:/Project_Demo/frontend/src/components/assets/images/roomimg";

            String fileName = StringUtils.cleanPath(imgdata.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imgdata.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            System.out.println("Upload Path: " + uploadDir);

            Room room = new Room();
            room.setPrice(Float.parseFloat(price));
            room.setRoomType(roomType);
            room.setRoomId(Integer.parseInt(roomId));
            room.setFloor(Integer.parseInt(floor));
            room.setRoom_status("empty");
            room.setRoom_info(information);

            room.setImg_url("src/components/assets/images/roomimg/" + fileName);

            roomService.updateRoom(room);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Add Success");
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error uploading image.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
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
    public ResponseEntity<Map<String, String>> updateRoom(
            @PathVariable int id,
            @RequestParam("photo") MultipartFile imgdata,
            @RequestParam("roomType") String roomType,
            @RequestParam("price") String price,
            @RequestParam("roomId") String roomId,
            @RequestParam("floor") String floor,
            @RequestParam("room_status") String status,
            @RequestParam("room_info") String information) {
        Room existingRoom = roomService.getRoomById(id);

        try {
            String uploadDir = "E:/Project_Demo/frontend/src/components/assets/images/roomimg";

            String fileName = StringUtils.cleanPath(imgdata.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = imgdata.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            if (existingRoom != null) {
                existingRoom.setPrice(Float.parseFloat(price));
                existingRoom.setRoomType(roomType);
                existingRoom.setRoomId(Integer.parseInt(roomId));
                existingRoom.setFloor(Integer.parseInt(floor));
                existingRoom.setRoom_status(status);
                existingRoom.setRoom_info(information);
                existingRoom.setImg_url("/src/components/assets/images/roomimg/" + fileName);


                roomService.updateRoom(existingRoom);

            }

            Map<String, String> response = new HashMap<>();
            response.put("message", "Add Success");
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error uploading image.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }
    }


    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailableRooms() {
        List<Room> roomList = roomService.getAvailableRoom();
        if (roomList == null) {
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