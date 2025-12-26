
import React, { useEffect, useState } from 'react';
import { API } from '../services/api';

export default function Wallet() {
  const [amount, setAmount] = useState('');
  const [balance, setBalance] = useState(null);
  const [msg, setMsg] = useState('');

  const load = () => API.get('/wallet/balance').then(r => setBalance(r.data.balance));

  useEffect(() => { load(); }, []);

  const deposit = async () => {
    try {
      const r = await API.post('/wallet/deposit', { amount: parseFloat(amount) });
      setBalance(r.data.balance);
      setMsg('Deposit successful!');
      setAmount('');
    } catch (e) {
      setMsg(e.response?.data?.message || e.message);
    }
  };

  return (
    <div style={{ padding: '2rem', minHeight: '100vh', background: '#f5f5f5' }}>
      <h2 style={{ textAlign: 'center', color: '#333', marginBottom: '1rem' }}>Wallet</h2>
      
      <div style={{ maxWidth: 400, margin: '0 auto', padding: 20, background: '#fff', borderRadius: 10, boxShadow: '0 4px 12px rgba(0,0,0,0.1)' }}>
        <p style={{ fontSize: '1.2rem', marginBottom: '1rem' }}><b>Balance:</b> ₹{balance ?? '...'}</p>
        
        <div style={{ display: 'flex', gap: 8, marginBottom: 12 }}>
          <input 
            placeholder="Amount" 
            type="number"
            min="1"
            value={amount} 
            onChange={e => setAmount(e.target.value)} 
            style={{ flex: 1, padding: 8, borderRadius: 6, border: '1px solid #ccc' }}
          />
          <button 
            onClick={deposit} 
            style={{ padding: '8px 16px', borderRadius: 6, background: '#36d1dc', color: '#fff', border: 'none', cursor: 'pointer' }}
          >
            Add Money
          </button>
        </div>

        {msg && <p style={{ color: msg.includes('successful') ? 'green' : 'red', marginTop: 8 }}>{msg}</p>}
      </div>
    </div>
  );
}
