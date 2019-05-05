package com.todo.service.repository;


import com.todo.service.entity.TaskDetails;
import com.todo.service.entity.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the TaskDetails entity.
 */
@Repository
public interface CustomTaskDetailsRepository extends TaskDetailsRepository {

    Optional<List<TaskDetails>> findByUserDetails(UserDetails userDetails);
}
