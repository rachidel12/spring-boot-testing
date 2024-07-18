package id.test.springboottesting.service;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.repository.GameRepository;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;

    private Game game1;
    private Game game2;

    @BeforeEach
    public void setUp() {
        game1 = new Game(1L, "Game Test", "Genre Test", new Date(2021-01-01));
        game2 = new Game(2L, "Game Test 2", "Genre Test 2", new Date(2021-01-01));
    }

    @Test
    public void GameService_GetAllGames_ReturnsAllGames() {
        // Arrange
        when(gameRepository.findAll()).thenReturn(Arrays.asList(game1, game2));
        // Act
        List<Game> result = gameService.getAllGames();
        // Assert
        assertEquals(2, result.size());
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    public void GameService_GetGameById_ReturnsGame() {
        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game1));
        // Act
        Optional<Game> result = gameService.getGameById(1L);
        // Assert
        assertTrue(result.isPresent());
        assertEquals(game1, result.get());
        verify(gameRepository, times(1)).findById(1L);
    }

    @Test
    void GameService_CreateGame_ReturnsCreatedGame() {
        // Arrange
        when(gameRepository.save(any(Game.class))).thenReturn(game1);
        // Act
        Game result = gameService.createGame(game1);
        // Assert
        assertEquals(game1, result);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void GameService_UpdateGame_ReturnsUpdatedGame() {
        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game1));
        given(gameRepository.save(any(Game.class))).willAnswer(invocation -> invocation.getArgument(0));;
        // Act
        Game updatedGame = new Game();
        updatedGame.setTitle("Updated Title");
        Game result = gameService.updateGame(1L, updatedGame);
        // Assert
        assertEquals("Updated Title", result.getTitle());
        verify(gameRepository, times(1)).findById(1L);
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    public void GameService_UpdateGame_ThrowsGameNotFound() {
        Game game = new Game();
        game.setGame_id(1L);
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> gameService.updateGame(1L, game));
        verify(gameRepository, times(1)).findById(1L);
    }

    @Test
    public void GameService_DeleteGameById_ReturnVoid() {
        // Arrange
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());
        doAnswer(invocation -> {
            return Optional.empty();
        }).when(gameRepository).deleteById(1L);

        // Act
        gameService.deleteGame(1L);

        // Assert
        Optional<Game> result = gameRepository.findById(1L);
        assertTrue(result.isEmpty());
        verify(gameRepository, times(1)).deleteById(1L);
    }

}
