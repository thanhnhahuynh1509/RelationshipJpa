package com.trainning.train1.service;

import com.trainning.train1.entity.Role;
import com.trainning.train1.entity.User;
import com.trainning.train1.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        Role role = user.getRole();

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername()).password(user.getPassword()).roles(role.getRole()).build();

        return userDetails;
    }

}
