package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Role;
import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Repository.RoleRepository;
import com.project.ReservationSystem.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.*;
@Service
public class UserService implements IUserService{
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException(user.getEmail() + " already registered!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.print(user.getPassword());
        Role userRole = roleRepository.findByName("user").get();
        user.setRole(userRole);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
    @Override
    @Transactional
    public void deleteUser(String email) {
        User user = findByEmail(email);
        if(user != null){
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public User findByEmail(String email) {
        System.out.println(userRepository.findByEmail(email).toString());
        return userRepository.findByEmail(email)
                .orElseThrow(() ->new RuntimeException("User not found!"));
    }


}
