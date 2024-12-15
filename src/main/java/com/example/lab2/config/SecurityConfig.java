package com.example.lab2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.lab2.entity.User.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http     
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/booking/create").hasAuthority(UserRole.USER.name())
                                .requestMatchers("/api/booking/**").authenticated()

                                .requestMatchers("/api/post").permitAll()
                                .requestMatchers("/api/post/posts/page").permitAll()
                                .requestMatchers("/api/post/photo").permitAll()

                                .requestMatchers("/api/user/sign-up").anonymous()
                                .requestMatchers("/api/user/**").authenticated()                         
                                
                                .requestMatchers("/api/review/create").hasAuthority(UserRole.USER.name())
                                .requestMatchers("/api/review/booking").authenticated()
                                .requestMatchers("/api/review/**").permitAll()

                                .requestMatchers("/api/wishlist/**").hasAuthority(UserRole.USER.name())

                                .requestMatchers("/api/report/**").hasAuthority(UserRole.USER.name())

                                .requestMatchers("/api/test/**").permitAll()
                                .requestMatchers("/error").permitAll()

                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/v3/**").permitAll()

                                .anyRequest().authenticated()             
                )
                .httpBasic(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
    
    @Bean
    @SuppressWarnings("removal")
	AuthenticationManager authenticationManager(
        HttpSecurity http,
        UserDetailsService userDetailsService, 
        PasswordEncoder encoder
    ) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(encoder)
                   .and()
                   .build();
	}
}