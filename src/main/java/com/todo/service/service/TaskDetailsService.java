package com.todo.service.service;

import com.todo.service.domain.GenericResponse;
import com.todo.service.domain.TaskDetailsDomain;
import com.todo.service.entity.TaskDetails;
import com.todo.service.entity.UserDetails;
import com.todo.service.repository.CustomTaskDetailsRepository;
import com.todo.service.repository.CustomUserDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.todo.service.config.Constants.FAILED;
import static com.todo.service.config.Constants.SUCCESS;

/**
 * Class name : TaskDetailsService
 * Service class to handle requests from consumer. this layer talks to the database to get the requests
 * served.
 */
@Service
public class TaskDetailsService {

    @Autowired
    private HttpHeaders httpHeaders;

    @Autowired
    private CustomTaskDetailsRepository customTaskDetailsRepository;

    @Autowired
    private CustomUserDetailsRepository customUserDetailsRepository;

    private static Logger logger = LoggerFactory.getLogger(TaskDetailsService.class);

    /**
     * find all the Tasks associated with a user.
     *
     * @param name
     * @return
     */
    public ResponseEntity<GenericResponse> getTasksForUser(String name) {
        logger.debug("Request arrived with name {}", name);
        Optional<UserDetails> optionalUserDetails = customUserDetailsRepository.getByUserName(name);
        if (optionalUserDetails.isPresent()) {
            Optional<List<TaskDetails>> taskList = customTaskDetailsRepository.findByUserDetails(optionalUserDetails.get());
            if (taskList.isPresent()) {
                GenericResponse<List<TaskDetails>> genericResponse = new GenericResponse<>(SUCCESS, taskList.get());
                return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>(FAILED, "No Such User available");
            return ResponseEntity.badRequest().headers(httpHeaders).body(genericResponse);
        }
    }

    /**
     * Deleting the task that is no more required for the user's todo list.
     *
     * @param id
     * @return ResponseEntity, Http Status Code : Accepted if record exist, else not found.
     */
    public ResponseEntity<Void> deleteTask(Long id) {
        Optional<TaskDetails> optionalTaskDetails = customTaskDetailsRepository.findById(id);
        if (optionalTaskDetails.isPresent()) {
            customTaskDetailsRepository.deleteById(id);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * create a new task for the user.
     *
     * @param taskDomain
     * @return ResponseEntity with Generic Response, with HttpStatus as OK or BAD_REQUEST
     */
    public ResponseEntity<GenericResponse> createTask(TaskDetailsDomain taskDomain) {
        Optional<UserDetails> optionalUserDetails = customUserDetailsRepository.getByUserName(taskDomain.getUserName());
        if (optionalUserDetails.isPresent()) {
            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setTaskDescription(taskDomain.getTaskDescription());
            taskDetails.setTaskName(taskDomain.getTaskName());
            taskDetails.setUserDetails(optionalUserDetails.get());
            taskDetails.setEstimatedTime(taskDomain.getEstimatedTime());
            TaskDetails savedTask = customTaskDetailsRepository.save(taskDetails);
            GenericResponse<TaskDetails> genericResponse = new GenericResponse<>(SUCCESS, savedTask);
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>(FAILED, "No such User");
            return ResponseEntity.badRequest().headers(httpHeaders).body(genericResponse);
        }
    }

    /**
     * update the task for the user.
     *
     * @param taskDomain
     * @return ResponseEntity with HttpStatus as OK if successful, otherwise BAD_REQUEST
     */
    public ResponseEntity<GenericResponse> updateTask(TaskDetailsDomain taskDomain) {
        Optional<TaskDetails> optionalTaskDetails = customTaskDetailsRepository.findById(taskDomain.getTaskId());
        if (optionalTaskDetails.isPresent()) {
            TaskDetails taskDetails = optionalTaskDetails.get();
            taskDetails.setTaskName(taskDomain.getTaskName());
            taskDetails.setTaskDescription(taskDomain.getTaskDescription());
            taskDetails.setEstimatedTime(taskDomain.getEstimatedTime());
            TaskDetails updatedTask = customTaskDetailsRepository.save(taskDetails);
            GenericResponse<TaskDetails> genericResponse = new GenericResponse<>(SUCCESS, updatedTask);
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>(FAILED, "No Such Task");
            return ResponseEntity.badRequest().headers(httpHeaders).body(genericResponse);
        }
    }
}
