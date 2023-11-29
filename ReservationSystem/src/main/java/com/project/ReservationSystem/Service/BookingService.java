package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Booking;

import java.util.List;

public class BookingService implements IBookingService{
    @Override
    public void cancelBooking(int BookingId) {

    }

    @Override
    public String saveBooking(int RoomId, Booking booking_request) {
        return null;
    }

    @Override
    public Booking findByConfirmationCode(String ConfirmCode) {
        return null;
    }

    @Override
    public List<Booking> getAllBooking() {
        return null;
    }

    @Override
    public List<Booking> getBookingByEmail(String email) {
        return null;
    }
}
