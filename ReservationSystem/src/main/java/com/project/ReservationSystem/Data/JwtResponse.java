package com.project.ReservationSystem.Data;

import com.project.ReservationSystem.Model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class JwtResponse {
    private int id;
    private String email;
    private String token;
    private String type = "Bearer";
    private List<String> role;

    public JwtResponse(int id, String email, String token, List<String> role){
        this.id = id;
        this.email = email;
        this.token = token;
        this.role = role;
    }

}
