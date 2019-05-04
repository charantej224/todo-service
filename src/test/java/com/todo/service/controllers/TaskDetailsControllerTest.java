package com.todo.service.controllers;

import com.todo.service.domain.GenericResponse;
import com.todo.service.domain.TaskDetailsDomain;
import com.todo.service.service.TaskDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TaskDetailsControllerTest {

    @Mock
    private TaskDetailsService mockTaskDetailsService;

    @InjectMocks
    private TaskDetailsController taskDetailsControllerUnderTest;

    @Before
    public void setUp() {
        initMocks(this);
        GenericResponse<String> genericResponse = new GenericResponse<>("Success","response");
        when(mockTaskDetailsService.createTask(any(TaskDetailsDomain.class))).thenReturn(ResponseEntity.ok().body(genericResponse));
        when(mockTaskDetailsService.updateTask(any(TaskDetailsDomain.class))).thenReturn(ResponseEntity.ok().body(genericResponse));
        when(mockTaskDetailsService.getTasksForUser(anyString())).thenReturn(ResponseEntity.ok().body(genericResponse));
        when(mockTaskDetailsService.deleteTask(anyLong())).thenReturn(ResponseEntity.accepted().build());
    }

    @Test
    public void testCreateTaskDetails() {
        TaskDetailsDomain taskDetails = new TaskDetailsDomain();
        final ResponseEntity<GenericResponse> result = taskDetailsControllerUnderTest.createTaskDetails(taskDetails);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response",((GenericResponse)result.getBody()).getResponseBody());
    }

    @Test
    public void testUpdateTaskDetails() {
        final TaskDetailsDomain taskDetails = new TaskDetailsDomain();
        final ResponseEntity<GenericResponse> result = taskDetailsControllerUnderTest.updateTaskDetails(taskDetails);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response",((GenericResponse)result.getBody()).getResponseBody());
    }

    @Test
    public void testGetTaskDetails() {
        final String name = "name";
        final ResponseEntity<GenericResponse> expectedResult = null;
        final ResponseEntity<GenericResponse> result = taskDetailsControllerUnderTest.getTaskDetails(name);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("response",((GenericResponse)result.getBody()).getResponseBody());
    }

    @Test
    public void testDeleteTaskDetails() {
        final Long id = 0L;
        final ResponseEntity<Void> result = taskDetailsControllerUnderTest.deleteTaskDetails(id);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }
}
