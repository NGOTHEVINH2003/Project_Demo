package com.project.ReservationSystem.Controller;

import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class RoomControllerTest {
    @Mock
    private RoomService roomService; // Mocked dependency

    @InjectMocks
    private RoomController roomController; // Controller to be tested
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddRoom_Success() throws IOException, SQLException {
        // Mock request parameters
        String price = "100";
        String roomType = "Single";
        String roomId = "1";
        String floor = "1";
        String information = "Information";
        MultipartFile imgdata = new MockMultipartFile("imgdata", "test.jpg", "image/jpeg", "test".getBytes());

        // Mocking isRoomIdExists to return false, indicating the roomId doesn't exist
        when(roomService.isRoomIdExists(anyInt())).thenReturn(false);

        // Create a temporary directory for testing file upload
        String tempDir = System.getProperty("java.io.tmpdir");
        String uploadDir = tempDir + "/test-images/";
        String fileName = "test.jpg";

        // Perform the controller method invocation
        ResponseEntity<Map<String, String>> responseEntity = roomController.addRoom(price, roomType, imgdata, roomId, floor, information);

        // Verify if the response is successful
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify the message in the response
        Map<String, String> responseBody = responseEntity.getBody();
        assertEquals("Add Success", responseBody.get("message"));
    }
    @Test
    void testGetRoomsByType_Success() {
        // Mocking data
        String roomType = "Type";
        List<Room> mockedRooms = new ArrayList<>(); // Your mocked room list

        // Mocking roomService.getRoomsByType to return the mocked room list
        when(roomService.getRoomsByType(roomType)).thenReturn(mockedRooms);

        // Perform the controller method invocation
        ResponseEntity<List<Room>> responseEntity = roomController.getRoomsByType(roomType);

        // Verify if the response is successful
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify the returned room list
        List<Room> returnedRooms = responseEntity.getBody();
        assertEquals(mockedRooms, returnedRooms);
    }

    @Test
    void testGetRoomsByType_NotFound() {
        // Mocking data for a case where the room list is null
        String roomType = "NonExistingType";

        // Mocking roomService.getRoomsByType to return null
        when(roomService.getRoomsByType(roomType)).thenReturn(null);

        // Perform the controller method invocation
        ResponseEntity<List<Room>> responseEntity = roomController.getRoomsByType(roomType);

        // Verify if the response is not found (404)
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
