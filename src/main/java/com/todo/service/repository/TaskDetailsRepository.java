package com.todo.service.repository;


import com.todo.service.entity.TaskDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TaskDetails entity.
 */
@Repository
public interface TaskDetailsRepository extends JpaRepository<TaskDetails, Long> {

}
