package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    @Query("Select b from Booking b where b.confirmationCode like %:confirmationCode% ")
    Optional<Booking> findByConfirmationCode(String confirmationCode);
    @Query("Select b from Booking b where b.guestEmail like %:Email% ")
    List<Booking> findByGuestEmail(String Email);
}
