package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Booking;

import java.util.List;

public interface IBookingService {
    void cancelBooking(int BookingId);
    String saveBooking(int RoomId, Booking booking_request);

    Booking findByConfirmationCode(String ConfirmCode);

    List<Booking> getAllBooking();
    List<Booking> getBookingByEmail(String email);

}
