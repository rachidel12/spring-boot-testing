package id.test.springboottesting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import id.test.springboottesting.model.Game;

public interface GameRepository extends JpaRepository<Game, Long> {
}