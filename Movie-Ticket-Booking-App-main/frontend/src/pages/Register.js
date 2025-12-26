

import React, { useState } from 'react';
import { API } from '../services/api';

export default function Register() {
  const [form, setForm] = useState({ name: '', email: '', password: '' });
  const [msg, setMsg] = useState('');

  const submit = async (e) => {
    e.preventDefault();
    setMsg('');
    try {
      await API.post('/auth/register', form);
      setMsg('✅ Registered successfully! Please login.');
      setForm({ name: '', email: '', password: '' });
    } catch (e) {
      setMsg(e.response?.data?.message || e.message);
    }
  };

  return (
    <div style={{
      display: 'flex',
      justifyContent: 'center',
      alignItems: 'center',
      height: '100vh',
      background: 'linear-gradient(135deg, #36d1dc 0%, #5b86e5 100%)'
    }}>
      <div style={{
        background: '#fff',
        padding: '2rem',
        borderRadius: '12px',
        boxShadow: '0 6px 16px rgba(0,0,0,0.15)',
        width: '100%',
        maxWidth: '420px'
      }}>
        <h2 style={{ textAlign: 'center', marginBottom: '1rem', color: '#333' }}>Register</h2>

        <form onSubmit={submit} style={{ display: 'grid', gap: '12px' }}>
          <input
            placeholder="Full Name"
            type="text"
            value={form.name}
            onChange={e => setForm({ ...form, name: e.target.value })}
            required
            style={{
              padding: '10px',
              borderRadius: '8px',
              border: '1px solid #ccc',
              fontSize: '14px'
            }}
          />
          <input
            placeholder="Email"
            type="email"
            value={form.email}
            onChange={e => setForm({ ...form, email: e.target.value })}
            required
            style={{
              padding: '10px',
              borderRadius: '8px',
              border: '1px solid #ccc',
              fontSize: '14px'
            }}
          />
          <input
            placeholder="Password"
            type="password"
            value={form.password}
            onChange={e => setForm({ ...form, password: e.target.value })}
            required
            style={{
              padding: '10px',
              borderRadius: '8px',
              border: '1px solid #ccc',
              fontSize: '14px'
            }}
          />
          <button style={{
            padding: '12px',
            background: '#36d1dc',
            color: '#fff',
            fontWeight: 'bold',
            border: 'none',
            borderRadius: '8px',
            cursor: 'pointer',
            transition: '0.3s'
          }}>
            Register
          </button>
        </form>

        {msg && (
          <p style={{
            color: msg.includes('✅') ? 'green' : 'red',
            marginTop: '1rem',
            textAlign: 'center'
          }}>
            {msg}
          </p>
        )}
      </div>
    </div>
  );
}
