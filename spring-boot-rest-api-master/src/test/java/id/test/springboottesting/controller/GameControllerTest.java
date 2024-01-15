package id.test.springboottesting.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.service.GameService;

@WebMvcTest(controllers = GameController.class)
@ActiveProfiles("test")
class GameControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean   
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Game> gameList;

    @BeforeEach
    void setUp() {
        this.gameList = new ArrayList<>();
        this.gameList.add(new Game(1L, "Game Title", "Game Genre", new Date()));
        this.gameList.add(new Game(2L, "Game Title 2", "Game Genre 2", new Date()));
        this.gameList.add(new Game(3L, "Game Title 3", "Game Genre 3", new Date()));

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }

    @Test
    void getAllGames_ShouldReturnListOfGames() throws Exception {
        // Arrange
        when(gameService.getAllGames()).thenReturn(gameList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/games"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(gameList.size()));
    }

    @Test
    void getGameById_ExistingId_ShouldReturnGame() throws Exception {
        // Arrange
        Long gameId = 1L;
        Game game = new Game(1L, "Game 1", "Genre 1", new Date());
        when(gameService.getGameById(gameId)).thenReturn(Optional.of(game));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/games/{id}", gameId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.game_id").value(gameId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Game 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("Genre 1"));
    }

    @Test
    void getGameById_NonExistingId_ShouldReturnNotFound() throws Exception {
        // Arrange
        Long gameId = 1L;
        when(gameService.getGameById(gameId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/games/{id}", gameId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createGame_ShouldReturnCreatedGame() throws Exception {
        // Arrange
        Game gameToCreate = new Game(null, "New Game", "New Genre", new Date());
        Game createdGame = new Game(1L, "New Game", "New Genre", new Date());
        when(gameService.createGame(gameToCreate)).thenReturn(createdGame);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(gameToCreate)))
                .andExpect(status().isOk())
                // .andExpect(MockMvcResultMatchers.jsonPath("$.game_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(createdGame.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(createdGame.getGenre()));
    }

    @Test
    void updateGame_ExistingId_ShouldReturnUpdatedGame() throws Exception {
        // Arrange
        Long gameId = 1L;
        Game updatedGame = new Game(gameId, "Updated Game", "Updated Genre", new Date());
        when(gameService.updateGame(gameId, updatedGame)).thenReturn(updatedGame);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/games/{id}", gameId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedGame)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Game"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value("Updated Genre"));
    }

    @Test
    void deleteGame_ExistingId_ShouldReturnOk() throws Exception {
        // Arrange
        Long gameId = 1L;

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/games/{id}", gameId))
                .andExpect(status().isOk());
    }
}
