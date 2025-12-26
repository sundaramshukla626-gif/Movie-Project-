
import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { API } from '../services/api';

export default function Shows() {
  const { id } = useParams();
  const [shows, setShows] = useState([]);

  useEffect(() => {
    API.get(`/movies/${id}/shows`).then(res => setShows(res.data));
  }, [id]);

  return (
    <div style={{ padding: '2rem', minHeight: '100vh', background: '#f5f5f5' }}>
      <h2 style={{ textAlign: 'center', marginBottom: '1.5rem', color: '#333' }}>Shows</h2>
      <div style={{ overflowX: 'auto' }}>
        <table style={{ width: '100%', borderCollapse: 'collapse', background: '#fff', borderRadius: '8px', overflow: 'hidden', boxShadow: '0 4px 10px rgba(0,0,0,0.1)' }}>
          <thead style={{ background: '#36d1dc', color: '#fff' }}>
            <tr>
              <th style={{ padding: '12px' }}>Screen</th>
              <th style={{ padding: '12px' }}>Show Time</th>
              <th style={{ padding: '12px' }}>Total Seats</th>
              <th style={{ padding: '12px' }}>Available</th>
              <th style={{ padding: '12px' }}>Action</th>
            </tr>
          </thead>
          <tbody>
            {shows.map(s => (
              <tr key={s.id} style={{ borderBottom: '1px solid #eee' }}>
                <td style={{ padding: '12px', textAlign: 'center' }}>{s.screen}</td>
                <td style={{ padding: '12px', textAlign: 'center' }}>{s.showTime?.replace('T', ' ')}</td>
                <td style={{ padding: '12px', textAlign: 'center' }}>{s.totalSeats}</td>
                <td style={{ padding: '12px', textAlign: 'center' }}>{s.availableSeats}</td>
                <td style={{ padding: '12px', textAlign: 'center' }}>
                  <Link to={`/book/${s.id}`} style={{ color: '#36d1dc', textDecoration: 'none', fontWeight: 'bold' }}>Book</Link>
                </td>
              </tr>
            ))}
            {shows.length === 0 && (
              <tr>
                <td colSpan="5" style={{ padding: '12px', textAlign: 'center', color: '#555' }}>No shows available</td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
}
