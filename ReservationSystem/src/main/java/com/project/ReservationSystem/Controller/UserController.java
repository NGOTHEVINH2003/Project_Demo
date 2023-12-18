package com.project.ReservationSystem.Controller;

import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final IUserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers(){
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
    @GetMapping("profile/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        try{
            User u = userService.findByEmail(email);
            return ResponseEntity.ok(u);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email){
        try{
            userService.deleteUser(email);
            return ResponseEntity.ok("delete successfully!");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
