package com.todo.service.service;

import com.todo.service.domain.GenericResponse;
import com.todo.service.domain.TaskDetailsDomain;
import com.todo.service.entity.TaskDetails;
import com.todo.service.entity.UserDetails;
import com.todo.service.repository.CustomTaskDetailsRepository;
import com.todo.service.repository.CustomUserDetailsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.todo.service.config.Constants.FAILED;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class TaskDetailsServiceTest {

    @Mock
    private HttpHeaders mockHttpHeaders;
    @Mock
    private CustomTaskDetailsRepository mockCustomTaskDetailsRepository;
    @Mock
    private CustomUserDetailsRepository mockCustomUserDetailsRepository;

    @InjectMocks
    private TaskDetailsService taskDetailsServiceUnderTest;

    UserDetails userDetails;

    @Before
    public void setUp() {
        initMocks(this);
        userDetails = new UserDetails();
        userDetails.setUserName("testname");
        userDetails.setPassword("password");
        TaskDetails taskDetails1 = new TaskDetails();
        taskDetails1.setTaskDescription("task1");
        taskDetails1.setTaskName("test1");
        TaskDetails taskDetails2 = new TaskDetails();
        taskDetails2.setTaskDescription("task2");
        taskDetails2.setTaskName("test2");
        List<TaskDetails> taskList = new ArrayList<>();
        taskList.add(taskDetails1);
        taskList.add(taskDetails2);

        when(mockCustomUserDetailsRepository.getByUserName("testname")).thenReturn(Optional.of(userDetails));
        when(mockCustomTaskDetailsRepository.findByUserDetails(userDetails)).thenReturn(Optional.of(taskList));
        doNothing().when(mockCustomTaskDetailsRepository).deleteById(0L);
        when(mockCustomTaskDetailsRepository.save(any(TaskDetails.class))).thenReturn(taskDetails1);
        when(mockCustomTaskDetailsRepository.findById(0L)).thenReturn(Optional.of(taskDetails1));
    }

    @Test
    public void testGetTasksForUser() {
        final String name = "testname";
        final ResponseEntity<GenericResponse> result = taskDetailsServiceUnderTest.getTasksForUser(name);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        GenericResponse<List<TaskDetails>> genericResponse = (GenericResponse<List<TaskDetails>>)result.getBody();
        assertEquals(2, genericResponse.getResponseBody().size());
    }

    @Test
    public void testGetTasksForUser_noSuchUser() {
        final String name = "testname1";
        final ResponseEntity<GenericResponse> result = taskDetailsServiceUnderTest.getTasksForUser(name);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void testGetTasksForUser_userPresent_noTasks() {
        final String name = "testname";
        when(mockCustomTaskDetailsRepository.findByUserDetails(userDetails)).thenReturn(Optional.empty());
        final ResponseEntity<GenericResponse> result = taskDetailsServiceUnderTest.getTasksForUser(name);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    public void testDeleteTask() {
        final Long id = 0L;
        final ResponseEntity<Void> result = taskDetailsServiceUnderTest.deleteTask(id);
        assertEquals(HttpStatus.ACCEPTED, result.getStatusCode());
    }


    @Test
    public void testCreateTask() {
        TaskDetailsDomain taskDetailsDomain = new TaskDetailsDomain();
        taskDetailsDomain.setUserName("testname");
        final ResponseEntity<GenericResponse> result = taskDetailsServiceUnderTest.createTask(taskDetailsDomain);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        GenericResponse<TaskDetails> genericResponse = (GenericResponse<TaskDetails>)result.getBody();
        assertEquals("test1",genericResponse.getResponseBody().getTaskName());
    }

    @Test
    public void testCreateTask_nosuchUser() {
        TaskDetailsDomain taskDetailsDomain = new TaskDetailsDomain();
        taskDetailsDomain.setUserName("testname1");
        final ResponseEntity<GenericResponse> result = taskDetailsServiceUnderTest.createTask(taskDetailsDomain);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        GenericResponse<String> genericResponse = (GenericResponse<String>)result.getBody();
        assertEquals(FAILED,genericResponse.getStatusMessage());
    }

    @Test
    public void testUpdateTask() {
        TaskDetailsDomain taskDetailsDomain = new TaskDetailsDomain();
        taskDetailsDomain.setTaskId(0L);
        taskDetailsDomain.setTaskName("test");
        final ResponseEntity<GenericResponse> result = taskDetailsServiceUnderTest.updateTask(taskDetailsDomain);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        GenericResponse<TaskDetails> genericResponse = (GenericResponse<TaskDetails>)result.getBody();
        assertEquals("test",genericResponse.getResponseBody().getTaskName());
    }

    @Test
    public void testUpdateTask_noId() {
        TaskDetailsDomain taskDetailsDomain = new TaskDetailsDomain();
        taskDetailsDomain.setTaskId(1L);
        taskDetailsDomain.setTaskName("test");
        final ResponseEntity<GenericResponse> result = taskDetailsServiceUnderTest.updateTask(taskDetailsDomain);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        GenericResponse<String> genericResponse = (GenericResponse<String>)result.getBody();
        assertEquals(FAILED,genericResponse.getStatusMessage());
    }
}
