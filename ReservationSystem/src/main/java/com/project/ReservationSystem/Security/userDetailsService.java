package com.project.ReservationSystem.Security;

import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class userDetailsService implements UserDetailsService {
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return userDetails.buildUserDetails(u);
    }
}
