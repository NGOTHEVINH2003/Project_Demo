package com.project.ReservationSystem.Controller;

import com.project.ReservationSystem.Model.Booking;
import com.project.ReservationSystem.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
    @Autowired
    BookingService bookingService ;
    @GetMapping("/searchByComfirmationCode/{ConfirmationCode}")
    public ResponseEntity<?> searchByConfirmationCode(@PathVariable String ConfirmationCode) {
        Booking foundBooking = bookingService.findByConfirmationCode(ConfirmationCode);
        if (foundBooking != null) {
            return ResponseEntity.ok(foundBooking);
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
        return  new ResponseEntity<>(bookingList, HttpStatus.OK);
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
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save booking");
        }
    }

    @GetMapping("/ViewAllBooking")
    public ResponseEntity<List<Booking>> viewAllBooking() {
        List<Booking> bookings = bookingService.getAllBooking();
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(bookings, HttpStatus.FOUND);
        }
    }

    @GetMapping("/SearchByCustomerID/{customerId}")
    public ResponseEntity<List<Booking>> SearchByCustomerID(@PathVariable int customerId) {
        List<Booking> bookings = bookingService.getBookingByCustomerId(customerId);
        if (bookings.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(bookings, HttpStatus.FOUND);
        }
    }
}