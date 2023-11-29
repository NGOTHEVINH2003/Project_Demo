package com.project.ReservationSystem.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER,
    cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
    @JoinTable(name = "user_Role",
    joinColumns = @JoinColumn(name = "userId",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "roleId",referencedColumnName = "id"))
    private Collection<Role> roles = new HashSet<>();
}
