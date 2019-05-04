package com.todo.service.controllers;

import com.todo.service.entity.UserDetails;
import com.todo.service.service.UserDetailsImplService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsControllerTest {

    @Mock
    private UserDetailsImplService mockUserDetailsService;

    @InjectMocks
    private UserDetailsController userDetailsControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testCreateUserDetails_returnFalse() {
        final UserDetails userDetails = new UserDetails();
        when(mockUserDetailsService.userSignup(any(UserDetails.class))).thenReturn(Boolean.FALSE);
        final ResponseEntity<Void> result = userDetailsControllerUnderTest.createUserDetails(userDetails);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testCreateUserDetails_returnTrue() {
        final UserDetails userDetails = new UserDetails();
        when(mockUserDetailsService.userSignup(any(UserDetails.class))).thenReturn(Boolean.TRUE);
        final ResponseEntity<Void> result = userDetailsControllerUnderTest.createUserDetails(userDetails);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
