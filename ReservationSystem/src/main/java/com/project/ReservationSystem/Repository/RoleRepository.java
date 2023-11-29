package com.project.ReservationSystem.Repository;

import com.project.ReservationSystem.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String roleName);
    boolean existsByName(String roleName);
}
