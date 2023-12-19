package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    Optional<User> findById(int id);
}
