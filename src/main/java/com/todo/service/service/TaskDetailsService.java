package com.todo.service.service;

import com.todo.service.domain.GenericResponse;
import com.todo.service.domain.TaskDetailsDomain;
import com.todo.service.entity.TaskDetails;
import com.todo.service.entity.UserDetails;
import com.todo.service.repository.CustomTaskDetailsRepository;
import com.todo.service.repository.CustomUserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class name : TaskDetailsService
 *              Service class to handle requests from consumer. this layer talks to the database to get the requests
 *              served.
 */
@Service
public class TaskDetailsService {

    @Autowired
    HttpHeaders httpHeaders;

    @Autowired
    CustomTaskDetailsRepository customTaskDetailsRepository;

    @Autowired
    CustomUserDetailsRepository customUserDetailsRepository;

    /**
     * find all the Tasks associated with a user.
     * @param name
     * @return
     */
    public ResponseEntity<GenericResponse> getTasksForUser(String name) {
        Optional<UserDetails> optionalUserDetails = customUserDetailsRepository.getByUserName(name);
        if (optionalUserDetails.isPresent()) {
            Optional<List<TaskDetails>> taskList = customTaskDetailsRepository.findByUserDetails(optionalUserDetails.get());
            if (taskList.isPresent()) {
                GenericResponse<List<TaskDetails>> genericResponse = new GenericResponse<>("Success", taskList.get());
                return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>("Failed", "No Such User available");
            return ResponseEntity.badRequest().headers(httpHeaders).body(genericResponse);
        }
    }

    /**
     * Deleting the task that is no more required for the user's todo list.
     * @param id
     * @return
     */
    public ResponseEntity<Void> deleteTask(Long id) {
        customTaskDetailsRepository.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    /**
     * create a new task for the user.
     * @param taskDomain
     * @return
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
            GenericResponse<TaskDetails> genericResponse = new GenericResponse<>("Success", savedTask);
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>("Failed", "No such User");
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        }
    }

    /**
     * update the task for the user.
     * @param taskDomain
     * @return
     */
    public ResponseEntity<GenericResponse> updateTask(TaskDetailsDomain taskDomain) {
        Optional<TaskDetails> optionalTaskDetails = customTaskDetailsRepository.findById(taskDomain.getTaskId());
        if (optionalTaskDetails.isPresent()) {
            TaskDetails taskDetails = optionalTaskDetails.get();
            taskDetails.setTaskName(taskDomain.getTaskName());
            taskDetails.setTaskDescription(taskDomain.getTaskDescription());
            taskDetails.setEstimatedTime(taskDomain.getEstimatedTime());
            TaskDetails updatedTask = customTaskDetailsRepository.save(taskDetails);
            GenericResponse<TaskDetails> genericResponse = new GenericResponse<>("Success", updatedTask);
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>("Fail", "No Such Task");
            return ResponseEntity.badRequest().headers(httpHeaders).body(genericResponse);
        }
    }
}
