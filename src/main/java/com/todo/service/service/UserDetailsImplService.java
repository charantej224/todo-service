package com.todo.service.service;

import com.todo.service.repository.CustomUserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(UserDetailsImplService.class);

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<com.todo.service.entity.UserDetails> userDetailsEntity = userDetailsRepository.getByUserName(username);
        if (!userDetailsEntity.isPresent())
            throw new UsernameNotFoundException(username);
        return new User(userDetailsEntity.get().getUserName(), userDetailsEntity.get().getPassword(), emptyList());
    }

    /**
     * Help a new user to sign-up and then use the services for the todolist preparation.
     *
     * @param userDetails
     * @returns Boolean if the user is successfully saved, in all other cases, it returns false.
     */
    public Boolean userSignup(com.todo.service.entity.UserDetails userDetails) {
        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        try {
            userDetailsRepository.save(userDetails);
            return Boolean.TRUE;
        } catch (RuntimeException exception) {
            logger.error("Exception occurred", exception);
            return Boolean.FALSE;
        }
    }
}
