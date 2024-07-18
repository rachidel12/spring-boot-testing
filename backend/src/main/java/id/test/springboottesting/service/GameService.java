package id.test.springboottesting.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.repository.GameRepository;

@Service
public class GameService {
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Optional<Game> getGameById(Long Id) {
        return gameRepository.findById(Id);
    }
    
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Game updateGame(Long id, Game game) {
        Optional<Game> existingGame = gameRepository.findById(id);
        if (existingGame.isPresent()) {
            if (game.getTitle() != null) {
                existingGame.get().setTitle(game.getTitle());
            }
            if (game.getGenre() != null) {
                existingGame.get().setGenre(game.getGenre());
            }
            if (game.getRelease_date() != null) {
                existingGame.get().setRelease_date(game.getRelease_date());
            }
            return gameRepository.save(existingGame.get());
        } else {
            throw new EntityNotFoundException("Game with id " + game.getGame_id() + " not found");
        }
    }

    public void deleteGame(Long gameId) {
        gameRepository.deleteById(gameId);
    }
}