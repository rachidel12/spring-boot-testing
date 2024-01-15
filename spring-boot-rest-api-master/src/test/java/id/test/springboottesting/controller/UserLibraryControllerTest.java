package id.test.springboottesting.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.model.User;
import id.test.springboottesting.model.UserLibrary;
import id.test.springboottesting.service.GameService;
import id.test.springboottesting.service.UserLibraryService;


@WebMvcTest(UserLibraryController.class)
@ActiveProfiles("test")
class UserLibraryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLibraryService userLibraryService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<UserLibrary> userLibraryList;

    @BeforeEach
    void setUp() {
        this.userLibraryList = new ArrayList<>();
        this.userLibraryList.add(new UserLibrary(1L, 1L));
        this.userLibraryList.add(new UserLibrary(2L, 1L));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @Test
    void getAllUserLibraries_ShouldReturnListOfUserLibraries() throws Exception {
        // Arrange
        when(userLibraryService.getAllUserLibraries()).thenReturn(userLibraryList);

        // Act & Assert
        mockMvc.perform(get("/api/UserLibrary"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(userLibraryList.size()));
    }

    @Test
    void getGamesByUserId_ShouldReturnGames() throws Exception {
        // Arrange
        Long userId = 1L;
        List<Game> games = Arrays.asList(
                new Game(1L, "Game 1", "Genre 1", new Date()),
                new Game(2L, "Game 2", "Genre 2", new Date())
        );
        when(userLibraryService.getGamesByUserId(userId)).thenReturn(games);

        // Act & Assert
        mockMvc.perform(get("/api/UserLibrary/{userId}/games", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(games.size()));
    }

    @Test
    void addUserLibrary_ShouldReturnAddedUserLibrary() throws Exception {
        // Arrange
        UserLibrary userLibraryToAdd = new UserLibrary(1L, 1L);
        given(userLibraryService.addUserLibrary(any(UserLibrary.class))).willAnswer((invocation) -> userLibraryToAdd);
        // Act & Assert
        mockMvc.perform(post("/api/UserLibrary")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userLibraryToAdd)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(userLibraryToAdd.getUserId()))
                .andExpect(jsonPath("$.gameId").value(userLibraryToAdd.getGameId()));
    }

    @Test
    void deleteUserGame_ShouldReturnOk() throws Exception {
        // Arrange
        Long userId = 1L;
        Long gameId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/UserLibrary/{userId}/games/{gameId}", userId, gameId))
                .andExpect(status().isOk());
    }

    @Test
    void deleteAllUserGames_ShouldReturnOk() throws Exception {
        // Arrange
        Long userId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/UserLibrary/{userId}/games", userId))
                .andExpect(status().isOk());
    }
}