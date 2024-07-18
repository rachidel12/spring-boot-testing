import React, { useState, useEffect } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import UserLibraryPage from './UserLibraryPage';
import { Button } from '@mui/material';

const GameListPage = ({ user, onLogout }) => {
  const [games, setGames] = useState([]);
  const [showUserLibrary, setShowUserLibrary] = useState(false);

  useEffect(() => {
    // Simulate fetching games from an API (replace with actual API request)
    // In a real app, you might use a library like axios to make the API request
    const fetchGames = async () => {
      const response = await fetch('http://localhost:8080/api/games');
      const data = await response.json();
      setGames(data);
    };

    fetchGames();
  }, []);

  const handleAddToLibrary = (gameId) => {
    // Simulate adding a game to the user's library (replace with actual logic)
    console.log(`Game ${gameId} added to ${user.email}'s library`);
    fetch(`http://localhost:8080/api/UserLibrary`, {
      method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            "userId": JSON.parse(localStorage.getItem('user')).id,
            "gameId": gameId
        })
    });
    console.log({
        "userId": JSON.parse(localStorage.getItem('user')).id,
        "gameId": gameId
    });
  };

  const handleGoToUserLibrary = () => {
    setShowUserLibrary(true);
  };

  const handleReturnToGameList = () => {
    setShowUserLibrary(false);
  };

  if (showUserLibrary) {
    return <UserLibraryPage user={user} onLogout={onLogout} onReturnToGameList={handleReturnToGameList} />; 
  }

  return (
    <div>
        <div style={{ display: "flex", alignItems: "center", justifyContent:"center"}}>
            <h2 style={{ marginRight: "450px" }}>Welcome, {user.name}!</h2>
            <Button variant="contained" color="error" onClick={onLogout}>Log Out</Button>
        </div>
      

      <Button variant='contained' style={{marginLeft:'10px'}} onClick={handleGoToUserLibrary}>Go to My Library</Button>
      <h3>Game List</h3>
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Title</TableCell>
              <TableCell>Genre</TableCell>
              <TableCell>Release Date</TableCell>
              <TableCell>Actions</TableCell>
              {/* Add more table headers as needed */}
            </TableRow>
          </TableHead>
          <TableBody>
            {games.map((game) => (
              <TableRow key={game.game_id}>
                <TableCell>{game.game_id}</TableCell>
                <TableCell>{game.title}</TableCell>
                <TableCell>{game.genre}</TableCell>
                <TableCell>{game.release_date}</TableCell>
                <TableCell>
                    <Button onClick={() => handleAddToLibrary(game.game_id)}>Add to Library</Button>
                </TableCell>
                {/* Add more table cells for other game attributes */}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
  );
};

export default GameListPage;