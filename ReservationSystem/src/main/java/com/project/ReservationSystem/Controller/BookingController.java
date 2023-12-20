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
import java.time.LocalDate;

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
            return ResponseEntity.noContent().build();
        }
        List<BookingResponse> bookingResponseList = getListBookingRespone(bookingList);
        return  new ResponseEntity<>(bookingResponseList, HttpStatus.OK);
    }

    @GetMapping("/CancelBooking/{bookingId}")
    public ResponseEntity<String> cancelBooking(@PathVariable int bookingId) {
        String cancelResult = bookingService.cancelBooking(bookingId);
            return ResponseEntity.ok().body(cancelResult);
    }

    @PostMapping("/SaveBooking")
    public ResponseEntity<?> saveBooking(@RequestBody Booking booking) {
            booking.setConfirmationCode(generateConfirmationCode(6));
            Booking savedBooking = bookingService.saveBooking(booking);
            if (savedBooking != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Booking Success Your booking confirmation Code is: " + savedBooking.getConfirmationCode());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Booking Failed The room already reserved in this period of time.");
            }
    }

    @GetMapping("/ViewAllBooking")
    public ResponseEntity<List<BookingResponse>> viewAllBooking() {
        List<Booking> bookings = bookingService.getAllBooking();
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            List<BookingResponse> bookingResponseList = getListBookingRespone(bookings);
            return new ResponseEntity<>(bookingResponseList, HttpStatus.OK);

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
        bookingResponse.setConfirmationCode(booking.getConfirmationCode());
        if (booking.getRoom() != null) {
            Room room = roomService.getRoomById(booking.getRoom().getId());
            RoomResponse roomResponse = getRoomResponse(room);
            System.out.println(roomResponse);
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

    public  String generateConfirmationCode(int length) {
        String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        do {
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(randomIndex));
            }
        } while (!checkDuplicateCode(sb.toString()));
        return sb.toString();
    }

    public boolean checkDuplicateCode(String code) {
        return !bookingService.existsByConfirmationCode(code);
    }


}