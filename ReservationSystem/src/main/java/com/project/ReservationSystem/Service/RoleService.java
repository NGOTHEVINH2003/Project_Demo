package com.project.ReservationSystem.Service;

import com.project.ReservationSystem.Model.Role;
import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Repository.RoleRepository;
import com.project.ReservationSystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final IUserService userService;
    @Override
    public List<Role> getRoles() {
       return roleRepository.findAll();
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).get();
    }


}
