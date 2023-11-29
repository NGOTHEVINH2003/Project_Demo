package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Role;
import com.project.ReservationSystem.Model.User;

import java.util.List;

public interface IRoleService {
    List<Role> getRoles();
    Role findByName(String name);
}
