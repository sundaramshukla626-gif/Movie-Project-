

import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { API } from '../services/api';

export default function Movies() {
  const [movies, setMovies] = useState([]);

  useEffect(() => {
    API.get('/movies').then(res => setMovies(res.data));
  }, []);

  return (
    <div style={{ padding: '2rem', background: '#f5f5f5', minHeight: '100vh' }}>
      <h2 style={{ textAlign: 'center', marginBottom: '2rem', color: '#333' }}>Now Showing</h2>
      <div style={{
        display: 'grid',
        gridTemplateColumns: 'repeat(auto-fit, minmax(220px,1fr))',
        gap: '20px'
      }}>
        {movies.map(m => (
          <div key={m.id} style={{
            border: '1px solid #ddd',
            padding: '12px',
            borderRadius: '12px',
            background: '#fff',
            boxShadow: '0 4px 10px rgba(0,0,0,0.1)',
            transition: 'transform 0.2s',
          }}
            onMouseEnter={e => e.currentTarget.style.transform = 'scale(1.05)'}
            onMouseLeave={e => e.currentTarget.style.transform = 'scale(1)'}
          >
            {m.posterUrl && (
              <img
                alt={m.title}
                src={m.posterUrl}
                style={{
                  width: '100%',
                  height: '280px',
                  objectFit: 'cover',
                  borderRadius: '12px'
                }}
              />
            )}
            <h3 style={{ marginTop: '12px', color: '#222' }}>{m.title}</h3>
            <p style={{ fontSize: '14px', color: '#555' }}>{m.description}</p>
            <p style={{ fontWeight: 'bold', color: '#333' }}>Base Price: ₹{m.basePrice}</p>
            <Link to={`/movies/${m.id}/shows`} style={{
              display: 'inline-block',
              marginTop: '8px',
              padding: '8px 12px',
              background: '#36d1dc',
              color: '#fff',
              borderRadius: '8px',
              textDecoration: 'none',
              textAlign: 'center'
            }}>View Shows</Link>
          </div>
        ))}
      </div>
    </div>
  );
}
