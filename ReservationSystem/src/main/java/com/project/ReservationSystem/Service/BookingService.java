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
            return "Cancel Successfully";
        } else {
            return "Not Found";
        }
    }

    @Override
    @Transactional
    public String saveBooking( Booking bookingRequest) {
        Booking savedBooking = bookingRepository.save(bookingRequest);
        return "Booking had been saved " ;
    }

    @Override
    public Booking findByConfirmationCode(String confirmCode) {
        return bookingRepository.findByConfirmationCode(confirmCode)
                .orElse(null);
    }

    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingByEmail(String email) {
        return bookingRepository.findByGuestEmail(email);
    }
}
