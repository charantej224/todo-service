package com.todo.service.service;

import com.todo.service.repository.CustomUserDetailsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class UserDetailsImplServiceTest {

    @Mock
    private CustomUserDetailsRepository mockUserDetailsRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    @InjectMocks
    private UserDetailsImplService userDetailsImplServiceUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        com.todo.service.entity.UserDetails userDetails = new com.todo.service.entity.UserDetails();
        userDetails.setPassword("password");
        userDetails.setUserName("username");
        when(mockUserDetailsRepository.getByUserName("username")).thenReturn(Optional.of(userDetails));
    }

    @Test
    public void testLoadUserByUsername() {
        final String username = "username";
        final UserDetails result = userDetailsImplServiceUnderTest.loadUserByUsername(username);
        assertEquals("password", result.getPassword());
        assertEquals("username", result.getUsername());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByUsername_nameNotFound() {
        final String username = "username1";
        final UserDetails result = userDetailsImplServiceUnderTest.loadUserByUsername(username);
    }

    @Test
    public void testUserSignup() {
        final com.todo.service.entity.UserDetails userDetails = new com.todo.service.entity.UserDetails();
        when(mockUserDetailsRepository.save(userDetails)).thenReturn(userDetails);
        final Boolean result = userDetailsImplServiceUnderTest.userSignup(userDetails);
        assertTrue(result);
    }

    @Test
    public void testUserSignup_ExceptionCases() {
        final com.todo.service.entity.UserDetails userDetails = new com.todo.service.entity.UserDetails();
        when(mockUserDetailsRepository.save(userDetails)).thenThrow(new RuntimeException("constraint violated"));
        final Boolean result = userDetailsImplServiceUnderTest.userSignup(userDetails);
        assertFalse(result);
    }
}
