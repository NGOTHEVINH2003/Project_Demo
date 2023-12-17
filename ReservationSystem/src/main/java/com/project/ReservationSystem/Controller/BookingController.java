package com.project.ReservationSystem.Controller;

import com.project.ReservationSystem.Data.BookingResponse;
import com.project.ReservationSystem.Data.RoomResponse;
import com.project.ReservationSystem.Model.Booking;
import com.project.ReservationSystem.Service.BookingService;
import com.project.ReservationSystem.Model.Room;
import com.project.ReservationSystem.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.security.SecureRandom;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    @Autowired
    BookingService bookingService ;
    @Autowired
    RoomService roomService;
    @GetMapping("/searchByComfirmationCode/{ConfirmationCode}")

    public ResponseEntity<?> searchByConfirmationCode(@PathVariable String ConfirmationCode) {
        Booking foundBooking = bookingService.findByConfirmationCode(ConfirmationCode);
        if (foundBooking != null) {
            BookingResponse foundBookingResponse = getBookingResponse(foundBooking);
            return ResponseEntity.ok(foundBookingResponse);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/searchByMail/{email}")
    public ResponseEntity<?>  searchByMail(@PathVariable String email){
        List<Booking> bookingList = bookingService.getBookingByEmail(email);
        if(bookingList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not Found");
        }
        List<BookingResponse> bookingResponseList = getListBookingRespone(bookingList);
        return  new ResponseEntity<>(bookingResponseList, HttpStatus.OK);
    }

    @GetMapping("/CancelBooking/{bookingID}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bookingID) {
        String cancelResult = bookingService.cancelBooking(bookingID);
            return ResponseEntity.ok().body(cancelResult);
    }

    @PostMapping("/SaveBooking")
    public ResponseEntity<?> saveBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingService.saveBooking(booking);
        if (savedBooking != null) {
            BookingResponse savedBookingResponse = getBookingResponse(savedBooking);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBookingResponse);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save booking");
        }
    }

    @GetMapping("/ViewAllBooking")
    public ResponseEntity<List<BookingResponse>> viewAllBooking() {
        List<Booking> bookings = bookingService.getAllBooking();
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<BookingResponse> bookingResponseList = getListBookingRespone(bookings);
            return new ResponseEntity<>(bookingResponseList, HttpStatus.FOUND);

        }
    }

    @GetMapping("/SearchByCustomerID/{customerId}")
    public ResponseEntity<List<BookingResponse>> SearchByCustomerID(@PathVariable int customerId) {
        List<Booking> bookings = bookingService.getBookingByCustomerId(customerId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<BookingResponse> bookingResponseList = getListBookingRespone(bookings);
            return new ResponseEntity<>(bookingResponseList, HttpStatus.FOUND);
        }
    }

    public BookingResponse getBookingResponse(Booking booking) {
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(booking.getBookingId());
        bookingResponse.setCheckIn(booking.getCheckIn());
        bookingResponse.setCheckOut(booking.getCheckOut());
        bookingResponse.setGuestFullName(booking.getGuestFullName());
        bookingResponse.setGuestEmail(booking.getGuestEmail());
        bookingResponse.setNumOfAdult(booking.getNumOfAdult());
        bookingResponse.setNumOfChildren(booking.getNumOfChildren());
        bookingResponse.setTotalGuest(booking.getTotalGuest());
        String confimationCode = generateConfimationCode(6);
        bookingResponse.setConfirmationCode(confimationCode);
        if (booking.getRoom() != null) {
            Room room = roomService.GetRoomById(booking.getRoom().getId());
            RoomResponse roomResponse = getRoomResponse(room);
            bookingResponse.setRoom(roomResponse);
        }
        return bookingResponse;
    }

    public List<BookingResponse> getListBookingRespone(List<Booking> bookingList){
        List<BookingResponse> bookingResponseList = new ArrayList<>();
        for(Booking booking : bookingList){
             BookingResponse bookingResponse = getBookingResponse(booking);
            bookingResponseList.add(bookingResponse);
        }
        return bookingResponseList;
    }

    public RoomResponse getRoomResponse(Room room) {
        RoomResponse roomResponse = new RoomResponse();
        roomResponse.setId(room.getId());
        roomResponse.setRoomType(room.getRoomType());
        roomResponse.setPrice(room.getPrice());
        roomResponse.setBooked (room.isBooked());
        roomResponse.setPhoto(room.getImg_url());
        List<Booking> bookingList = bookingService.getBookingByRoomId(room.getId());
        return roomResponse;
    }

    public static String generateConfimationCode(int length) {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

}