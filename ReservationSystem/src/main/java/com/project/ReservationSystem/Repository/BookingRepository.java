package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    List<Booking> findByRoomId(int RoomId);
    Optional<Booking> findByConfirmationCode(String confirmCode);
    List<Booking> findByGuestEmail(String email);
}
