package com.todo.service.repository;

import com.todo.service.entity.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomUserDetailsRepository extends UserDetailsRepository {

    Optional<UserDetails> getByUserName(String userName);
}
