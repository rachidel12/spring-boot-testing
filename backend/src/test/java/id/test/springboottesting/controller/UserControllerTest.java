package id.test.springboottesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import id.test.springboottesting.exception.UserRegistrationException;
import id.test.springboottesting.model.User;
import id.test.springboottesting.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<User> userList;

    @BeforeEach
    void setUp() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(1L, "user1@gmail.com", "pwd1","User1"));
        this.userList.add(new User(2L, "user2@gmail.com", "pwd2","User2"));
        this.userList.add(new User(3L, "user3@gmail.com", "pwd3","User3"));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @Test
    void shouldFetchAllUsers() throws Exception {

        given(userService.findAllUsers()).willReturn(userList);

        this.mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(userList.size())));
    }

    @Test
    void shouldFetchOneUserById() throws Exception {
        final Long userId = 1L;
        final User user = new User(1L, "ten@mail.com","teten","teten");

        given(userService.findUserById(userId)).willReturn(Optional.of(user));

        this.mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.name", is(user.getName())));
    }

    @Test
    void shouldReturn404WhenFindUserById() throws Exception {
        final Long userId = 1L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());

        this.mockMvc.perform(get("/api/user/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        given(userService.createUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        User user = new User(null, "newuser1@gmail.com", "pwd", "Name");

        this.mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.name", is(user.getName())));
    }

    @Test
    void shouldReturn400WhenCreateNewUserWithoutEmail() throws Exception {
        User user = new User(null, null, "pwd", "Name");

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void shouldUpdateUser() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "user1@gmail.com", "pwd", "Name");
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        given(userService.updateUser(any(Long.class),any(User.class))).willAnswer((invocation) -> user);

        this.mockMvc.perform(put("/api/users/{id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.name", is(user.getName())));

    }

    @Test
    void shouldReturn400WhenUpdatingNonExistingUser() throws Exception {
        Long userId = 1L;
        User user = new User(null, "user1@gmail.com", "pwd", "Name");

        // Simulate that the user does not exist
        given(userService.findUserById(userId)).willReturn(Optional.empty());

        // Simulate that the update operation throws a UserRegistrationException
        given(userService.updateUser(any(Long.class), any(User.class)))
                .willThrow(new UserRegistrationException("User with id " + userId + " does not exist"));

        this.mockMvc.perform(put("/api/users/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.Status", is("400 BAD_REQUEST")))
                .andExpect(jsonPath("$.Cause", is("User with id 1 does not exist")))
                .andExpect(jsonPath("$.Solution", is("Please enter a valid entity with proper constraints")));
}


    @Test
    void shouldDeleteUser() throws Exception {
        Long userId = 1L;
        User user = new User(userId, "user1@gmail.com", "pwd", "Name");
        given(userService.findUserById(userId)).willReturn(Optional.of(user));
        doNothing().when(userService).deleteUserById(user.getId());

        this.mockMvc.perform(delete("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.password", is(user.getPassword())))
                .andExpect(jsonPath("$.name", is(user.getName())));

    }

    @Test
    void shouldReturn404WhenDeletingNonExistingUser() throws Exception {
        Long userId = 1L;
        given(userService.findUserById(userId)).willReturn(Optional.empty());

        this.mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNotFound());

    }

}