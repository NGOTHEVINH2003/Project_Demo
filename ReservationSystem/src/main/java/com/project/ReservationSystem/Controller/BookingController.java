package com.project.ReservationSystem.Controller;

import com.project.ReservationSystem.Model.Booking;
import com.project.ReservationSystem.Service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
public class BookingController {
 @Autowired
    BookingService bookingService ;
    @GetMapping("/searchByComfirmationCode/{ComfirmationCode}")
    public Booking  searchByComfirmationCode(@PathVariable String ComfirmationCode){
        return bookingService.findByConfirmationCode(ComfirmationCode);
    }

    @GetMapping("/searchByMail/{emai}")
    public List<Booking>  searchByMail(@PathVariable String email){
        return bookingService.getBookingByEmail(email);
    }

    @GetMapping("/CancelBooking")
    public String  CancelBooking(@RequestAttribute int BookingID){
        return bookingService.cancelBooking(BookingID);
    }

    @GetMapping("/SaveBooking")
    public String SaveBooking(@RequestAttribute Booking booking){
        return bookingService.saveBooking(booking);
    }


}
