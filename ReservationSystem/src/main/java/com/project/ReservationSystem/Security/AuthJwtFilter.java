package com.project.ReservationSystem.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthJwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthJwtFilter.class);
    @Autowired
    private jwtUtils utils;
    @Autowired
    private userDetailsService userDetailsService;

    private String parseToken(HttpServletRequest request){
        String Auth = request.getHeader("Authorization");
        if(StringUtils.hasText(Auth) && Auth.startsWith("Bearer ")){
            return Auth.substring(7);
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseToken(request);
            if(jwt != null && utils.validateToken(jwt)){
                String email = utils.extractEmail(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (Exception e){
            logger.error("Unable to set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
