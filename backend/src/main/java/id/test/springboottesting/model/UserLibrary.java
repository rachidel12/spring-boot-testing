package id.test.springboottesting.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import id.test.springboottesting.primarykeys.UserLibraryId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "UserLibrary")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserLibraryId.class)
public class UserLibrary {

    @Id
    private Long userId;

    @Id
    private Long gameId;

    // @Id
    // @OneToOne
    // @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
    // private User user;

    // @Id
    // @ManyToOne
    // @JoinColumn(name = "gameId", referencedColumnName = "game_id", nullable = false)
    // private Game game;
} 