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

@Service
public class TaskDetailsService {

    @Autowired
    HttpHeaders httpHeaders;

    @Autowired
    CustomTaskDetailsRepository customTaskDetailsRepository;

    @Autowired
    CustomUserDetailsRepository customUserDetailsRepository;

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

    public ResponseEntity<Void> deleteTask(Long id) {
        customTaskDetailsRepository.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<GenericResponse> createTask(TaskDetailsDomain taskDomain) {
        Optional<UserDetails> optionalUserDetails = customUserDetailsRepository.getByUserName(taskDomain.getUserName());
        if (optionalUserDetails.isPresent()) {
            TaskDetails taskDetails = new TaskDetails();
            taskDetails.setTaskDescription(taskDomain.getTaskDescription());
            taskDetails.setTaskName(taskDomain.getTaskName());
            taskDetails.setUserDetails(optionalUserDetails.get());
            TaskDetails savedTask = customTaskDetailsRepository.save(taskDetails);
            GenericResponse<TaskDetails> genericResponse = new GenericResponse<>("Success", savedTask);
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>("Failed", "No such User");
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        }
    }

    public ResponseEntity<GenericResponse> updateTask(TaskDetailsDomain taskDomain) {
        TaskDetails taskDetails = customTaskDetailsRepository.getOne(taskDomain.getTaskId());
        if (null != taskDetails) {
            taskDetails.setTaskName(taskDomain.getTaskName());
            taskDetails.setTaskDescription(taskDomain.getTaskDescription());
            TaskDetails updatedTask = customTaskDetailsRepository.save(taskDetails);
            GenericResponse<TaskDetails> genericResponse = new GenericResponse<>("Success", updatedTask);
            return ResponseEntity.ok().headers(httpHeaders).body(genericResponse);
        } else {
            GenericResponse<String> genericResponse = new GenericResponse<>("Fail", "No Such Task");
            return ResponseEntity.badRequest().headers(httpHeaders).body(genericResponse);
        }
    }
}
