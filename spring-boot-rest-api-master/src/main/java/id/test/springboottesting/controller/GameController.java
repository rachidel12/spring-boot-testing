package id.test.springboottesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.service.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return gameService.getGameById(id)
                .map(game -> ResponseEntity.ok(game)) // game found, return 200
                .orElseGet(() -> ResponseEntity.notFound().build()); // game not found, return 404
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        return gameService.createGame(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game game) {
        return gameService.updateGame(id, game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok("Game deleted successfully");
    }
}
