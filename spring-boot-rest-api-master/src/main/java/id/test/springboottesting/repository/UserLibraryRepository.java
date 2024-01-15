package id.test.springboottesting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import id.test.springboottesting.model.Game;
import id.test.springboottesting.model.UserLibrary;
import id.test.springboottesting.primarykeys.UserLibraryId;
import java.util.List;

public interface UserLibraryRepository extends JpaRepository<UserLibrary, UserLibraryId> {

    @Query(value = "SELECT g.* FROM games g INNER JOIN UserLibrary ul ON g.game_id = ul.gameId WHERE ul.userId = :userId", nativeQuery = true)
    List<Object[]> findGamesByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserLibrary ul WHERE ul.userId = :userId")
    void deleteByUserId(Long userId);

    void deleteByUserIdAndGameId(Long userId, Long gameId);
}