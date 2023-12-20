package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Booking;
import com.project.ReservationSystem.Model.Room;
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
    RoomService roomService;
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
        List<Booking> bookingList = getBookingByRoomId(bookingRequest.getRoom().getId());
        Room room = roomService.getRoomById(bookingRequest.getRoom().getId());
        bookingRequest.setRoom(room);
        bookingRequest.setTotalGuest(bookingRequest.getNumOfAdult() + bookingRequest.getNumOfChildren());
        if(checkAvailableBookingRoom(bookingRequest,bookingList)){
            return bookingRepository.save(bookingRequest);
        }
        return null;
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

    public boolean checkAvailableBookingRoom(Booking booking,List<Booking> bookingList){
        if(bookingList.isEmpty()){
            return true;
        }
        for (Booking bk: bookingList) {
            if(booking.getCheckIn().isEqual(bk.getCheckIn()) || booking.getCheckOut().isEqual(bk.getCheckOut())) {
                return false;
            }
            if(booking.getCheckOut().isBefore(bk.getCheckIn()) || booking.getCheckIn().isAfter(bk.getCheckOut())){
                return true;
            }
        }
        return false;
    }
}