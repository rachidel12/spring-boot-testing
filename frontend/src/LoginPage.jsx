import React, { useState } from 'react';
import "./LoginForm.css";

const LoginPage = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onLogin(email, password);
  };

  return (
    // <div>
    //   <h2>Login Page</h2>
    //   <form onSubmit={handleSubmit}>
    //     <label>
    //       Email:
    //       <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
    //     </label>
    //     <br />
    //     <label>
    //       Password:
    //       <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
    //     </label>
    //     <br />
    //     <button type="submit">Log In</button>
    //   </form>
    // </div>
    <div id="login-form">
      <h1>Login</h1>
      <form onSubmit={handleSubmit}> 
        <label htmlFor="email">Email :</label>
        <input type="text" id="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        <label htmlFor="password">Password :</label>
        <input type="password" id="password" value={password} onChange={(e) => setPassword(e.target.value)} />
        <input type="submit" value="Submit" />
      </form>
    </div>
  );
};

export default LoginPage;