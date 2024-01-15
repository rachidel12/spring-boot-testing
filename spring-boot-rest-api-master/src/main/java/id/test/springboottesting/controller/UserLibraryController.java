package id.test.springboottesting.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.model.UserLibrary;
import id.test.springboottesting.service.UserLibraryService;

@RestController
@RequestMapping("/api/UserLibrary")
public class UserLibraryController {

    private final UserLibraryService userLibraryService;

    @Autowired
    public UserLibraryController(UserLibraryService userLibraryService) {
        this.userLibraryService = userLibraryService;
    }

    @GetMapping
    public ResponseEntity<List<UserLibrary>> getAllUserLibraries() {
        List<UserLibrary> userLibraries = userLibraryService.getAllUserLibraries();
        return new ResponseEntity<>(userLibraries, HttpStatus.OK);
    }

    @GetMapping("/{userId}/games")
    public ResponseEntity<List<Game>> getGamesByUserId(@PathVariable Long userId) {
        List<Game> games = userLibraryService.getGamesByUserId(userId);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserLibrary> addUserLibrary(@RequestBody UserLibrary userLibrary) {
        UserLibrary addedUserLibrary = userLibraryService.addUserLibrary(userLibrary);
        return new ResponseEntity<>(addedUserLibrary, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{userId}/games/{gameId}")
    public ResponseEntity<String> deleteUserGame(@PathVariable Long userId, @PathVariable Long gameId) {
        userLibraryService.deleteUserGame(userId, gameId);
        return ResponseEntity.ok("Game deleted Successfully from UserLibrary");
    }

    @DeleteMapping("/{userId}/games")
    public ResponseEntity<String> deleteAllUserGames(@PathVariable Long userId) {
        userLibraryService.deleteAllUserGames(userId);
        return ResponseEntity.ok("UserLibrary deleted Sucessfully");
    }
}