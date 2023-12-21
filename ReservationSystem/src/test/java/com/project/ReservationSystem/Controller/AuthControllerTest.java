package com.project.ReservationSystem.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.ReservationSystem.Data.JwtResponse;
import com.project.ReservationSystem.Data.LoginRequest;
import com.project.ReservationSystem.Model.Role;
import com.project.ReservationSystem.Model.User;
import com.project.ReservationSystem.Security.userDetails;
import com.project.ReservationSystem.Security.userDetailsService;
import com.project.ReservationSystem.Security.jwtUtils;
import com.project.ReservationSystem.Service.IUserService;
import com.project.ReservationSystem.Service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @MockBean
    private userDetailsService userDetailsService;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private jwtUtils jwtUtils;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegisterUser() throws Exception {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test@email.com");
        user.setPassword("testpass");

        Mockito.when(userService.registerUser(Mockito.any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("Register success!"));
    }


    @Test
    public void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("testpassword");

        // Create a User object
        User user = new User();
        user.setId(1);
        user.setFirstName("test");
        user.setLastName("test");
        user.setEmail("test@example.com");
        user.setPassword("testpassword");
        user.setRole(new Role("user"));

        // Create userDetails using buildUserDetails method
        Authentication auth = Mockito.mock(Authentication.class);
        userDetails userDetails = Mockito.mock(userDetails.class);
        Collection<? extends GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        userDetails Details = userDetails.buildUserDetails(user);


        Mockito.when(userService.registerUser(Mockito.any(User.class))).thenReturn(user);


        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenReturn(auth);
        Mockito.when((userDetails)auth.getPrincipal()).thenReturn(Details);
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString()))
                .thenReturn(Details);
        Mockito.when(userDetails.getAuthorities()).thenReturn((List)authorities);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andReturn();

        JwtResponse jwtResponse = objectMapper.readValue(result.getResponse().getContentAsString(), JwtResponse.class);
    }
}
