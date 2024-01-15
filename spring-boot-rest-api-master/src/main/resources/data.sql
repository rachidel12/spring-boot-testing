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
(3, 'Call of Duty: Modern Warfare III', 'Shooter', '2023-10-08'),
(4, 'Outer Wilds: Archaeologist Edition', 'Adventure, Indie, Puzzle', '2021-09-28'),
(5, 'Prince of Persia: The Lost Crown', 'Adventure, Platform', '2024-01-18'),
(6, 'Gears 5', 'Adventure, Shooter', '2019-09-09'),
(7, 'The Last of Us Part II', 'Adventure, Shooter', '2020-06-19'),
(8, 'The Legend of Zelda: Breath of the Wild 2', 'Adventure, Role-playing (RPG)', '2022-12-31'),
(9, 'The Elder Scrolls VI', 'Adventure, Role-playing (RPG)', '2023-12-31'),
(10, 'Cyberpunk 2077', 'Adventure, Role-playing (RPG)', '2020-12-10'),
(11, 'The Witcher 4', 'Adventure, Role-playing (RPG)', '2023-12-31'),
(12, 'The Elder Scrolls V: Skyrim', 'Adventure, Role-playing (RPG)', '2011-11-11'),
(13, 'The Witcher 3: Wild Hunt', 'Adventure, Role-playing (RPG)', '2015-05-19'),
(14, 'The Witcher 2: Assassins of Kings', 'Adventure, Role-playing (RPG)', '2011-05-17'),
(15, 'The Witcher', 'Adventure, Role-playing (RPG)', '2007-10-26'),
(16, 'The Elder Scrolls IV: Oblivion', 'Adventure, Role-playing (RPG)', '2006-03-20'),
(17, 'The Elder Scrolls III: Morrowind', 'Adventure, Role-playing (RPG)', '2002-05-01'),
(18, 'The Elder Scrolls II: Daggerfall', 'Adventure, Role-playing (RPG)', '1996-08-31'),
(19, 'The Elder Scrolls: Arena', 'Adventure, Role-playing (RPG)', '1994-03-25'),
(20, 'The Witcher: Rise of the White Wolf', 'Adventure, Role-playing (RPG)', '2023-12-31'),
(21, 'The Witcher: Versus', 'Adventure, Role-playing (RPG)', '2023-12-31'),
(22, 'The Witcher: Crimson Trail', 'Adventure, Role-playing (RPG)', '2023-12-31'),
(23, 'The Witcher: Adventure Game', 'Adventure, Role-playing (RPG)', '2023-12-31');

-- Sample data for UserLibrary table
INSERT INTO UserLibrary (userId, gameId) VALUES
(1, 1),  -- User 1 has 'Game A' in their library
(1, 2), -- User 1 has 'Game B' in their library
(2, 1), -- User 2 has 'Game A' in their library
(2, 2),  -- User 2 has 'Game B' in their library
(2, 3);  -- User 3 has 'Game C' in their library 

