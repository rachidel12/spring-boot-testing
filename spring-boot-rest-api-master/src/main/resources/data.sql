DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS games;
DROP TABLE IF EXISTS UserLibrary;

CREATE TABLE users(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(20),
    email VARCHAR(20) UNIQUE,
    password varchar(100) NOT NULL
);

-- Create Game table
CREATE TABLE IF NOT EXISTS games (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    genre VARCHAR(255),
    release_date Date
);

CREATE TABLE IF NOT EXISTS UserLibrary (
    userId INT,
    gameId INT,
    PRIMARY KEY (userId, gameId),
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY (gameId) REFERENCES games(game_id)
);

INSERT INTO users (name, email, password ) VALUES   ( 'admin', 'admin@mail.com', 'SampleP@s123' ),
                                                    ( 'johny', 'johny@mail.com', 'SampleP@s123' ),
                                                    ( 'ramazan', 'rmzn@mail.com', 'SampleP@s123' ),
                                                    ( 'teten-nugraha', 'teten@mail.com', 'SampleP@s123' );

-- Sample data for Game table
INSERT INTO games (game_id, title, genre, release_date) VALUES
(1, 'Marvel''s Spider-Man 2', 'Adventure, Hack and slash/Beat''em up', '2023-10-20'),
(2, 'The Finals', 'Shooter, Tactical', '2023-10-26'),
(3, 'Call of Duty: Modern Warfare III', 'Shooter', '2023-10-08');

-- Sample data for UserLibrary table
INSERT INTO UserLibrary (userId, gameId) VALUES
(1, 1),  -- User 1 has 'Game A' in their library
(1, 2), -- User 1 has 'Game B' in their library
(2, 1), -- User 2 has 'Game A' in their library
(2, 2),  -- User 2 has 'Game B' in their library
(2, 3);  -- User 3 has 'Game C' in their library 

