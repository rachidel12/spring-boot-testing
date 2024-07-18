package id.test.springboottesting.service;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.model.UserLibrary;
import id.test.springboottesting.repository.UserLibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserLibraryServiceTest {

    @Mock
    private UserLibraryRepository userLibraryRepository;

    private UserLibraryService userLibraryService;

    private UserLibrary userLibrary1;
    private UserLibrary userLibrary2;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        userLibraryService = new UserLibraryService(userLibraryRepository);
        userLibrary1 = new UserLibrary(1L, 1L);
        userLibrary2 = new UserLibrary(1L, 2L);
    }

    @Test
    void testGetAllUserLibraries() {
        // Arrange
        when(userLibraryRepository.findAll()).thenReturn(Arrays.asList(userLibrary1, userLibrary2));
        // Act
        List<UserLibrary> result = userLibraryService.getAllUserLibraries();
        // Assert
        assertEquals(2, result.size());
        verify(userLibraryRepository, times(1)).findAll();
    }

    @Test
    void testGetGamesByUserId() {
        // Arrange
        List<Object[]> gameObjects = Arrays.asList(new Object[] {1L, "Game Title", "Game Genre", new Date()},
                                    new Object[] {2L, "Game Title 2", "Game Genre 2", new Date()}); 
        when(userLibraryRepository.findGamesByUserId(1L)).thenReturn(gameObjects);
        // Act
        List<Game> result = userLibraryService.getGamesByUserId(1L);
        // Assert
        assertEquals(2, result.size());
        assertEquals("Game Title", result.get(0).getTitle());
        verify(userLibraryRepository, times(1)).findGamesByUserId(1L);
    }

    @Test
    void testAddUserLibrary() {
        // Arrange
        when(userLibraryRepository.save(userLibrary1)).thenReturn(userLibrary1);
        // Act
        UserLibrary result = userLibraryService.addUserLibrary(userLibrary1);
        // Assert
        assertEquals(userLibrary1, result);
        verify(userLibraryRepository, times(1)).save(userLibrary1);
    }

    @Test
    void testDeleteUserGame() {
        userLibraryService.deleteUserGame(1L, 1L);

        verify(userLibraryRepository, times(1)).deleteByUserIdAndGameId(1L, 1L);
    }

    @Test
    void testDeleteAllUserGames() {
        userLibraryService.deleteAllUserGames(1L);

        verify(userLibraryRepository, times(1)).deleteByUserId(1L);
    }
}