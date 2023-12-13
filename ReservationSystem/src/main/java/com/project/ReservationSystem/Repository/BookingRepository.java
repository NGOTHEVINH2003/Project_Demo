package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Optional<Booking> findByConfirmationCode(String confirmationCode);
    List<Booking> findByGuestEmail(String Email);
    @Query("Select * from Booking where customer_id like %:customer_id%")
    List<Booking> findByCustomerId(int customer_id);
}
