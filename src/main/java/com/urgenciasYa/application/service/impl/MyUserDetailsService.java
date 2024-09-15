package com.urgenciasYa.application.service.impl;

import com.urgenciasYa.domain.model.UserEntity;
import com.urgenciasYa.infrastructure.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MyUserDetailsService implements UserdetailService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
