package com.todo.service.controllers;

import com.codahale.metrics.annotation.Timed;
import com.todo.service.domain.GenericResponse;
import com.todo.service.domain.TaskDetailsDomain;
import com.todo.service.entity.TaskDetails;
import com.todo.service.service.TaskDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;

/**
 * REST controller for managing TaskDetails.
 */
@RestController
@RequestMapping("/api")
public class TaskDetailsController {

    private final Logger log = LoggerFactory.getLogger(TaskDetailsController.class);

    @Autowired
    TaskDetailsService taskDetailsService;


    /**
     * POST  /task-details : Create a new taskDetails.
     *
     * @param taskDetails the taskDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskDetails, or with status 400 (Bad Request) if the taskDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-details")
    @Timed
    public ResponseEntity<GenericResponse> createTaskDetails(@Valid @RequestBody TaskDetailsDomain taskDetails) {
        log.debug("REST request to save TaskDetails : {}", taskDetails);
        return taskDetailsService.createTask(taskDetails);
    }

    /**
     * PUT  /task-details : Updates an existing taskDetails.
     *
     * @param taskDetails the taskDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskDetails,
     * or with status 400 (Bad Request) if the taskDetails is not valid,
     * or with status 500 (Internal Server Error) if the taskDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-details")
    @Timed
    public ResponseEntity<TaskDetails> updateTaskDetails(@Valid @RequestBody TaskDetailsDomain taskDetails) {
        log.debug("REST request to update TaskDetails : {}", taskDetails);
        return taskDetailsService.updateTask(taskDetails);
    }

    /**
     * GET  /task-details/:id : get the "id" taskDetails.
     *
     * @param name the id of the taskDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskDetails, or with status 404 (Not Found)
     */
    @GetMapping("/task-details")
    @Timed
    public ResponseEntity<GenericResponse> getTaskDetails(@RequestParam String name) {
        log.debug("REST request to get TaskDetails : {}", name);
        return taskDetailsService.getTasksForUser(name);
    }

    /**
     * DELETE  /task-details/:id : delete the "id" taskDetails.
     *
     * @param id the id of the taskDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/task-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteTaskDetails(@PathVariable Long id) {
        log.debug("REST request to delete TaskDetails : {}", id);
        return taskDetailsService.deleteTask(id);
    }
}
