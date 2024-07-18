import React, { useState, useEffect } from 'react';
import { Table, TableContainer, TableHead, TableRow, TableCell, TableBody, Paper, Button } from '@mui/material';

const UserLibraryPage = ({ user, onLogout, onReturnToGameList }) => {
    const [userLibrary, setUserLibrary] = useState([]);

    useEffect(() => {
        const fetchUserLibrary = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/UserLibrary/${user.id}/games`);
            if (!response.ok) {
                throw new Error('User library fetch failed');
            }
            const userLibraryData = await response.json();
            setUserLibrary(userLibraryData);
        } catch (error) {
            console.error(error);
        }
        };
        fetchUserLibrary();
    }, []);

    const handleReturnToGameList = () => {
        // Implement any logic needed before returning to the GameListPage
        onReturnToGameList();
    };

    return (
    <div>
        {/* Display user library data here */}
        <div style={{marginTop: '20px', marginLeft:'20px'}}>
            <Button variant='contained' color='primary' onClick={handleReturnToGameList}>Return to Game List</Button>
        </div>
        <h2>{user.name}'s Game Library</h2>
        <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>Game ID</TableCell>
              <TableCell>Title</TableCell>
              <TableCell>Genre</TableCell>
              <TableCell>Release Date</TableCell>
              <TableCell>Actions</TableCell>
              {/* Add more table headers as needed */}
            </TableRow>
          </TableHead>
          <TableBody>
            {userLibrary.map((libraryItem) => (
              <TableRow key={libraryItem.game_id}>
                <TableCell>{libraryItem.game_id}</TableCell>
                <TableCell>{libraryItem.title}</TableCell>
                <TableCell>{libraryItem.genre}</TableCell>
                <TableCell>{libraryItem.release_date}</TableCell>
                <TableCell> <Button variant='contained' color='error'>Supprimer</Button> </TableCell>
                {/* Add more table cells for other library item attributes */}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </div>
    );
};

export default UserLibraryPage;
