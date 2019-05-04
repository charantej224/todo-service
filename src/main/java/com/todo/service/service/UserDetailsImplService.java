package com.todo.service.service;

import com.todo.service.repository.CustomUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsImplService implements UserDetailsService {

    @Autowired
    private CustomUserDetailsRepository userDetailsRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.todo.service.entity.UserDetails> userDetailsEntity = userDetailsRepository.getByUserName(username);
        if (!userDetailsEntity.isPresent())
            throw new UsernameNotFoundException(username);
        return new User(userDetailsEntity.get().getUserName(), userDetailsEntity.get().getPassword(), emptyList());
    }

    /**
     * Help a new user to sign-up and then use the services for the todo list preparation.
     * @param userDetails
     * @return
     */
    public Boolean userSignup(com.todo.service.entity.UserDetails userDetails){
        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        userDetailsRepository.save(userDetails);
        return Boolean.TRUE;
    }
}
