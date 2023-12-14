package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Optional<Booking> findByConfirmationCode(String confirmationCode);
    List<Booking> findByGuestEmail(String Email);
    @Query("SELECT b FROM Booking b WHERE b.customer.id = :customerId")
    List<Booking> findByCustomerId( int customerId);
    @Query("SELECT b FROM Booking b WHERE b.room.id = :room_id")
    List<Booking> findByRoomId( int room_id);
}
