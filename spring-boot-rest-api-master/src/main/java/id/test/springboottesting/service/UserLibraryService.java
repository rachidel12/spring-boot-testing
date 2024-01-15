package id.test.springboottesting.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.model.UserLibrary;
import id.test.springboottesting.repository.UserLibraryRepository;

@Service
public class UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;

    @Autowired
    public UserLibraryService(UserLibraryRepository userLibraryRepository) {
        this.userLibraryRepository = userLibraryRepository;
    }

    public List<UserLibrary> getAllUserLibraries() {
        return userLibraryRepository.findAll();
    }

    public List<Game> getGamesByUserId(Long userId) {
        List<Object[]> objects = userLibraryRepository.findGamesByUserId(userId);
        List<Game> games = new ArrayList<>();
        for (Object[] object : objects) {
            Game game = new Game();
            game.setGame_id(Long.parseLong(object[0].toString()));
            game.setTitle(object[1].toString());
            game.setGenre(object[2].toString());
            game.setRelease_date((Date) object[3]);
            games.add(game);
        }
        return games;
    }

    public UserLibrary addUserLibrary(UserLibrary userLibrary) {
        return userLibraryRepository.save(userLibrary);
    }

    public void deleteUserGame(Long userId, Long gameId) {
        userLibraryRepository.deleteByUserIdAndGameId(userId, gameId);
    }

    public void deleteAllUserGames(Long userId) {
        userLibraryRepository.deleteByUserId(userId);
    }
}