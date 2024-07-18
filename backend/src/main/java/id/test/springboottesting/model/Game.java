package id.test.springboottesting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "games")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long game_id;

    @NotEmpty(message = "Title should not be empty")
    @Column(nullable = false, length = 100)
    private String title;

    @NotEmpty(message = "Genre should not be empty")
    @Column(nullable = false, length = 100)
    private String genre;

    @NotNull(message = "Release Date should not be null")
    @Column(nullable = false, length = 7)
    private Date release_date;
}
