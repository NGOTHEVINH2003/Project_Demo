package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    String deleteByEmail(String email);

    Optional<User> findById(int id);
}
