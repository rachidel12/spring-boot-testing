// import { render, screen } from '@testing-library/react';
// import App from './App';

// beforeAll(() => {
//   jest.spyOn(window, 'getUserFromLocalStorage').mockReturnValue({ name: 'Test User', email: 'testuser@example.com' });
// });

// test('renders GameListPage when user is logged in', () => {
//   render(<App />);
//   expect(screen.getByText(/Welcome, Test User!/i)).toBeInTheDocument();
// });

// test('renders LoginPage when user is not logged in', () => {
//   jest.spyOn(window, 'getUserFromLocalStorage').mockReturnValue(null);
//   render(<App />);
//   expect(screen.getByText(/Login/i)).toBeInTheDocument();
// });

import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

const localStorageMock = {
    getItem: jest.fn(),
    setItem: jest.fn(),
    removeItem: jest.fn(),
    clear: jest.fn(),
  };
  global.localStorage = localStorageMock;

describe('App', () => {

  afterEach(() => {
    localStorage.removeItem('user');
  });

  test('renders LoginPage when there is no user', () => {
    render(<App />);
    expect(screen.getByText(/login/i)).toBeInTheDocument();
  });
});