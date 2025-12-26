import React from 'react';
import { Routes, Route, Link, Navigate } from 'react-router-dom';
import Register from './pages/Register';
import Login from './pages/Login';
import Movies from './pages/Movies';
import Shows from './pages/Shows';
import Wallet from './pages/Wallet';
import Book from './pages/Book';
import MyBookings from './pages/MyBookings';

const isAuthed = () => !!localStorage.getItem('token');

const Private = ({children}) => isAuthed() ? children : <Navigate to="/login" replace />;

export default function App(){
  return (
    <div style={{maxWidth: 900, margin: '0 auto', padding: 16}}>
      <nav style={{display: 'flex', gap: 12, marginBottom: 16}}>
        <Link to="/">Movies</Link>
        <Link to="/wallet">Wallet</Link>
        <Link to="/bookings">My Bookings</Link>
        {isAuthed() ? (
          <button onClick={() => {localStorage.removeItem('token'); localStorage.removeItem('user'); window.location.href='/';}}>Logout</button>
        ) : (
          <>
            <Link to="/register">Register</Link>
            <Link to="/login">Login</Link>
          </>
        )}
      </nav>
      <Routes>
        <Route path="/" element={<Movies />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/movies/:id/shows" element={<Shows />} />
        <Route path="/wallet" element={<Private><Wallet /></Private>} />
        <Route path="/book/:showId" element={<Private><Book /></Private>} />
        <Route path="/bookings" element={<Private><MyBookings /></Private>} />
      </Routes>
    </div>
  );
}