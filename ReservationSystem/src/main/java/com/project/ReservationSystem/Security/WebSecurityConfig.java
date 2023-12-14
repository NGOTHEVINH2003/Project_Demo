package com.project.ReservationSystem.Security;

import jakarta.servlet.FilterRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity(securedEnabled = true,jsr250Enabled = true,prePostEnabled = true)
@EnableWebMvc
public class WebSecurityConfig {
    private final userDetailsService userDetailsService;
    private final AuthEntryPoint Entrypoint;
    private final boolean Credential = true;
    private final String url = "http://localhost:5173";
    private final Long Max_Age = 3000L;
    private final int Cors_Order = -102;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception{
       return authConfig.getAuthenticationManager();
    }
    @Bean
    public AuthJwtFilter filter(){
        return new AuthJwtFilter();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain chain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(c -> c.disable()).exceptionHandling(exception -> exception.authenticationEntryPoint(Entrypoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        authorization -> authorization.requestMatchers("/auth/**","/booking/**" ,"/room/**","/user/**").permitAll()
                        .requestMatchers("/admin/**").hasRole("admin")
                        .anyRequest().authenticated());
        httpSecurity.authenticationProvider(authenticationProvider());
        httpSecurity.addFilterBefore(filter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }


    @Bean
    public FilterRegistrationBean CORSFilter(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration cors_config = new CorsConfiguration();
        cors_config.setAllowCredentials(true);
        cors_config.addAllowedOrigin(url);
        cors_config.setAllowedHeaders(Arrays.asList(
                HttpHeaders.AUTHORIZATION,
                HttpHeaders.CONTENT_TYPE,
                HttpHeaders.ACCEPT
        ));
        cors_config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.PATCH.name()
        ));

        cors_config.setMaxAge(Max_Age);
        source.registerCorsConfiguration("/**", cors_config);
        FilterRegistrationBean filter = new FilterRegistrationBean(new CorsFilter(source));
        filter.setOrder(Cors_Order);
        return filter;
    }
}
