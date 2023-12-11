package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    void deleteUser(String email);
    User findByEmail(String email);
    User registerUser(User user);
}
