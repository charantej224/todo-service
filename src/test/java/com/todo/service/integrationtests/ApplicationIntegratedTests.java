package com.todo.service.integrationtests;

import com.todo.service.TodoserviceApp;
import com.todo.service.domain.TaskDetailsDomain;
import com.todo.service.entity.TaskDetails;
import com.todo.service.entity.UserDetails;
import com.todo.service.repository.CustomTaskDetailsRepository;
import com.todo.service.repository.CustomUserDetailsRepository;
import com.todo.service.repository.TaskDetailsRepository;
import com.todo.service.repository.UserDetailsRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.server.LocalManagementPort;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@EnableJpaRepositories("com.todo.service.repository")
@EntityScan("com.todo.service.*")
@ComponentScan("com.todo.service.*")
@SpringBootTest(classes = {TodoserviceApp.class})
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:config/application.yml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ApplicationIntegratedTests {

    @LocalServerPort
    int randomServerPort;

    @LocalManagementPort
    int randomManagementPort;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CustomUserDetailsRepository userDetailsRepository;

    @Autowired
    CustomTaskDetailsRepository taskDetailsRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private static String token;
    private static String AUTH_HEADER_NAME = "Authorization";

    @Before
    public void setup() {
    }

    private static Logger logger = LoggerFactory.getLogger(ApplicationIntegratedTests.class);


    @Test
    public void test_stage1_signup_userInputs() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.post("/sign-up").content(getUserDetailsAsString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(200,status);
    }

    @Test
    public void test_stage2_login_userInputs() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.post("/login").content(getUserDetailsAsString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(rb).andReturn();
        token = result.getResponse().getHeaders(AUTH_HEADER_NAME).get(0);
        logger.info(token);
        Assert.assertTrue(!StringUtils.isBlank(token));
    }

    @Test
    public void test_stage3_createTask() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.post("/api/task-details").content(getnewTaskDetailsAsString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header(AUTH_HEADER_NAME, token);;
        MvcResult result = mockMvc.perform(rb).andReturn();
        String resultValue = result.getResponse().getContentAsString();
        logger.info(resultValue);
        assertTrue(resultValue.contains("test description"));
    }

    @Test
    public void test_stage4_updateTask() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.put("/api/task-details").content(getupdateTaskDetailsAsString()).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header(AUTH_HEADER_NAME, token);;
        MvcResult result = mockMvc.perform(rb).andReturn();
        String resultValue = result.getResponse().getContentAsString();
        logger.info(resultValue);
        assertTrue(resultValue.contains("Changed Test"));
        assertTrue(resultValue.contains("Changed Description"));
    }

    @Test
    public void test_stage5_updateTask() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.get("/api/task-details?name=admin").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header(AUTH_HEADER_NAME, token);;
        MvcResult result = mockMvc.perform(rb).andReturn();
        String resultValue = result.getResponse().getContentAsString();
        logger.info(resultValue);
        assertTrue(resultValue.contains("Changed Test"));
        assertTrue(resultValue.contains("Changed Description"));
    }

    @Test
    public void test_stage6_deleteTask() throws Exception {
        RequestBuilder rb = MockMvcRequestBuilders.delete("/api/task-details/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).header(AUTH_HEADER_NAME, token);;
        MvcResult result = mockMvc.perform(rb).andReturn();
        assertEquals(202,result.getResponse().getStatus());
    }

    private String getUserDetailsAsString() throws Exception {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("admin");
        userDetails.setPassword("admin");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(userDetails);
    }

    private String getnewTaskDetailsAsString() throws Exception {
        TaskDetailsDomain taskDetailsDomain = new TaskDetailsDomain();
        taskDetailsDomain.setUserName("admin");
        taskDetailsDomain.setTaskName("Test");
        taskDetailsDomain.setTaskDescription("test description");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(taskDetailsDomain);
    }

    private String getupdateTaskDetailsAsString() throws Exception {
        TaskDetailsDomain taskDetailsDomain = new TaskDetailsDomain();
        taskDetailsDomain.setTaskId(1L);
        taskDetailsDomain.setTaskName("Changed Test");
        taskDetailsDomain.setTaskDescription("Changed Description");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(taskDetailsDomain);
    }
}
