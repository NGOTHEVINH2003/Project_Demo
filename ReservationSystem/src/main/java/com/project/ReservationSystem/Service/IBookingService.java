package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Booking;

import java.util.List;

public interface IBookingService {
    String cancelBooking(int BookingId);
    Booking saveBooking(Booking booking_request);

    Booking findByConfirmationCode(String ConfirmCode);

    List<Booking> getAllBooking();
    List<Booking> getBookingByEmail(String email);

}
