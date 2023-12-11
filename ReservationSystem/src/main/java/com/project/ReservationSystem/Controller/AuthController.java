package com.project.ReservationSystem.Controller;


import com.project.ReservationSystem.Data.JwtResponse;
import com.project.ReservationSystem.Data.LoginRequest;
import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Security.jwtUtils;
import com.project.ReservationSystem.Security.userDetails;
import com.project.ReservationSystem.Service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    //login và Register
    private final IUserService service;
    private final AuthenticationManager authenticationManager;
    private final jwtUtils utils;
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        try{
            service.registerUser(user);
            return ResponseEntity.ok("Register success!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody LoginRequest request){
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = utils.generateUserJWT(auth);
        userDetails userDetails = (userDetails) auth.getPrincipal();
        List<String> role = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getId(),
                userDetails.getEmail(),
                jwt,
                role));
    }
}
