package com.todo.service.controllers;

import com.todo.service.entity.UserDetails;
import com.todo.service.service.UserDetailsImplService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for managing UserDetails.
 */
@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsImplService userDetailsService;

    private final Logger logger = LoggerFactory.getLogger(UserDetailsController.class);

    /**
     * POST  /sign-up" : Create a new userDetails.
     *
     * @param userDetails the userDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userDetails, or with status 400 (Bad Request) if the userDetails has already an ID
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserDetails> createUserDetails(@Valid @RequestBody UserDetails userDetails) {
        logger.debug("REST request to save UserDetails : {}", userDetails);
        if(userDetailsService.userSignup(userDetails))
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.badRequest().build();
    }


}
