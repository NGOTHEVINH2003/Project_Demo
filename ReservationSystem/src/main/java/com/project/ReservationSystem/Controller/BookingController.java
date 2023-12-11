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
    @GetMapping("/searchByComfirmationCode/{ComfirmationCode}")
    public ResponseEntity<?> searchByComfirmationCode(@PathVariable String ComfirmationCode) {
        Booking foundBooking = bookingService.findByConfirmationCode(ComfirmationCode);

        if (foundBooking != null) {
            return new ResponseEntity<>(foundBooking, HttpStatus.OK);
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
        return new ResponseEntity<>(bookingList, HttpStatus.FOUND) ;
    }

    @GetMapping("/CancelBooking")
    public ResponseEntity<?> cancelBooking(@RequestParam int bookingID) {
        return ResponseEntity.ok().body(bookingService.cancelBooking(bookingID));
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
    public ResponseEntity<List<Booking>>  ViewAllBooking(  ){
        return new ResponseEntity<>(bookingService.getAllBooking(),HttpStatus.FOUND);
    }
}
