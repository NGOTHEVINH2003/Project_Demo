package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Booking;
import com.project.ReservationSystem.Repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Component
public class BookingService implements IBookingService {
    @Autowired
    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    @Transactional
    public String cancelBooking(int bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            bookingRepository.delete(booking);
            return "Booking with ID " + bookingId + " has been canceled successfully.";
        } else {
            return "Booking with ID " + bookingId + " not found.";
        }
    }

    @Override
    @Transactional
    public Booking saveBooking( Booking bookingRequest) {
        Booking savedBooking;
        bookingRequest.setTotalGuest(bookingRequest.getNumOfAdult() + bookingRequest.getNumOfChildren());
        try {
            savedBooking = bookingRepository.save(bookingRequest);
            return savedBooking ;
        }catch (Exception e){
            throw new RuntimeException("Failed to save booking", e);
        }
    }

    @Override
    public Booking findByConfirmationCode(String confirmationCode) {
        return bookingRepository.findByConfirmationCode(confirmationCode).orElse(null);
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingByEmail(String guestEmail) {
        return bookingRepository.findByGuestEmail(guestEmail);
    }

    @Override
    public List<Booking> getBookingByCustomerId(int CustomerId) {
        return bookingRepository.findByCustomerId(CustomerId);
    }

    @Override
    public List<Booking> getBookingByRoomId(int RoomId) {
        return bookingRepository.findByRoomId(RoomId);
    }
    public boolean existsByConfirmationCode(String confirmationCode){
        Optional<Booking> optionalBooking = bookingRepository.findByConfirmationCode(confirmationCode);
        return optionalBooking.isPresent();
    }
}