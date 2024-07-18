import React, { useState } from 'react';
import LoginPage from './LoginPage';
import GameListPage from './GameListPage';

const App = () => {
  const [user, setUser] = useState(getUserFromLocalStorage());

  const handleLogin = async (email, password) => {
    try {
      const queryParams = `?email=${encodeURIComponent(email)}&password=${encodeURIComponent(password)}`;
      const response = await fetch(`http://localhost:8080/api/users/login${queryParams}`,
      {method: 'POST', headers: {'Content-Type': 'application/json'}});

      if (!response.ok) {
        throw new Error('Authentication failed');
      }

      const userData = await response.json();
      setUser(userData);
      // Store user data in localStorage
      localStorage.setItem('user', JSON.stringify(userData));
    } catch (error) {
      console.error('Login error:', error);
      // Handle login error (show an error message to the user, etc.)
    }
  };

  const handleLogout = () => {
    setUser(null);
    // Remove user data from localStorage
    localStorage.removeItem('user');
  };

  function getUserFromLocalStorage() {
    const userData = localStorage.getItem('user');
    return userData ? JSON.parse(userData) : null;
  }

  return (
    <div>
      {user ? (
        <GameListPage user={user} onLogout={handleLogout} />
      ) : (
        <LoginPage onLogin={handleLogin} />
      )}
    </div>
  );
};

export default App;