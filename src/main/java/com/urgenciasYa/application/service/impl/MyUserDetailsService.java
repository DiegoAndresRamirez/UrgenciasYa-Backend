package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.domain.model.UserPrincipal;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Retrieve the user entity from the repository using the username
        UserEntity user = userRepository.findByName(username);
        // If the user is not found, log a message and throw an exception
        if (user == null) {
            System.out.println("User Not Found"); // Log message for debugging
            throw new UsernameNotFoundException("user not found"); // Throw exception
        }

        // Return a UserPrincipal object containing user details
        return new UserPrincipal(user);
    }
}
