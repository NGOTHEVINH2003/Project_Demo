package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.User;

import java.util.List;

public interface IUserService {
    User registerUser(User user);
    List<User> getUsers();
    void deleteUser(String email);
    User findUserByEmail(String email);
}
