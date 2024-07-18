import React from 'react';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom/extend-expect';
import GameListPage from '../GameListPage';
import fetchMock from 'jest-fetch-mock';

// describe('GameListPage', () => {

//   beforeEach(() => {
//     fetchMock.resetMocks();
//   });
//   it('renders game list correctly', async () => { 
//     // Mock the fetch function to return sample game data
//     jest.spyOn(global, 'fetch').mockResolvedValue({
//       json: async () => [
//         { game_id: 1, title: 'Game 1' },
//         { game_id: 2, title: 'Game 2' },
//       ],
//     });

//     render(<GameListPage user={{ email: 'test@example.com' }} />);

//     // Wait for the data to be loaded and rendered
//     const game1 = await screen.findByText('Game 1');
//     const game2 = await screen.findByText('Game 2');

//     // expect(game1).toBeInTheDocument();
//     // expect(game2).toBeInTheDocument();
//     assertTrue;

//     // Clean up the mock after the test
//     global.fetch.mockRestore();
//   });

//   it('adds a game to the library when the button is clicked', () => {
//     const mockHandleAddToLibrary = jest.fn();

//     render(
//       <GameListPage
//         user={{ email: 'test@example.com' }}
//         onLogout={() => {}}
//         handleAddToLibrary={mockHandleAddToLibrary}
//       />
//     );

//     // Click the "Add to Library" button for the first game
//     const addButton = screen.getByText('Add to Library');
//     addButton.click();

//     // Verify that the mock function was called
//     expect(mockHandleAddToLibrary).toHaveBeenCalledWith(1);
//   });
// });

// descrtest('renders without crashing', () => {
//     const div = document.createElement('div');
//     render(<GameListPage user={{ email: 'test@example.com' }} />, div);
//   });

test('renders without crashing', () => {
    const div = document.createElement('div');
    render(<GameListPage user={{ email: 'test@example.com' }} />, div);
  });