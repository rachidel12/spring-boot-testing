package id.test.springboottesting.primarykeys;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class UserLibraryId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long gameId;

    public UserLibraryId() {
    }

    public UserLibraryId(Long userId, Long gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    // Equals and HashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLibraryId that = (UserLibraryId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(gameId, that.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, gameId);
    }
}
