import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { API } from "../services/api";

export default function Book() {
  const { showId } = useParams();
  const navigate = useNavigate();
  const [show, setShow] = useState(null);
  const [selectedSeats, setSelectedSeats] = useState([]);
  const [msg, setMsg] = useState("");

  const user = JSON.parse(localStorage.getItem("user"));

  // Fetch show details
  useEffect(() => {
    if (!showId) return;

    API.get(`/shows/${showId}`)
      .then(res => setShow(res.data))
      .catch(err => console.error(err));
  }, [showId]);

  const toggleSeat = (seat) => {
    if (show?.bookedSeats.includes(seat)) return;

    if (selectedSeats.includes(seat)) {
      setSelectedSeats(selectedSeats.filter(s => s !== seat));
    } else {
      setSelectedSeats([...selectedSeats, seat]);
    }
  };

  const handleBook = async () => {
    if (!selectedSeats.length) {
      setMsg("⚠️ Please select at least one seat");
      return;
    }

    try {
      const payload = {
        userId: user.id,
        showId: show.id,
        seats: selectedSeats,
      };

      const { data } = await API.post("/bookings/book", payload);

      setMsg(`🎉 Booking successful! Seats: ${data.seatNumbers.join(", ")}`);

      // Update booked seats in UI
      setShow(prev => ({
        ...prev,
        bookedSeats: [...prev.bookedSeats, ...data.seatNumbers],
      }));
      setSelectedSeats([]);

      setTimeout(() => navigate("/bookings"), 1500);
    } catch (err) {
      setMsg(err.response?.data?.message || err.message);
    }
  };

  if (!show) return <p style={{ textAlign: "center" }}>Loading show details...</p>;

  // Create seat grid
  const totalSeats = Array.from({ length: show.totalSeats }, (_, i) => `S${i + 1}`);
  const columns = 8; // number of seats per row

  return (
    <div style={{ padding: "2rem", minHeight: "100vh", background: "#f5f5f5" }}>
      <div style={{ maxWidth: 600, margin: "0 auto", background: "#fff", padding: "2rem", borderRadius: 12, boxShadow: "0 4px 12px rgba(0,0,0,0.1)" }}>
        <h2 style={{ textAlign: "center", color: "#2c3e50" }}>🎟️ Book Your Seats</h2>
        <p style={{ textAlign: "center", color: "#555" }}>Show: <strong>{show.movieTitle}</strong> | Screen: <strong>{show.screen}</strong></p>

        <div style={{
          display: "grid",
          gridTemplateColumns: `repeat(${columns}, 1fr)`,
          gap: 10,
          justifyItems: "center",
          margin: "20px 0"
        }}>
          {totalSeats.map((seat, idx) => {
            const isBooked = show.bookedSeats.includes(seat);
            const isSelected = selectedSeats.includes(seat);
            return (
              <div
                key={seat}
                onClick={() => toggleSeat(seat)}
                style={{
                  width: 45,
                  height: 45,
                  borderRadius: 6,
                  background: isBooked ? "#bdc3c7" : isSelected ? "#36d1dc" : "#ecf0f1",
                  border: "1px solid #7f8c8d",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                  fontWeight: "bold",
                  color: isBooked ? "#7f8c8d" : isSelected ? "#fff" : "#2c3e50",
                  cursor: isBooked ? "not-allowed" : "pointer",
                  transition: "0.2s",
                  userSelect: "none"
                }}
              >
                {idx + 1}
              </div>
            );
          })}
        </div>

        <p style={{ textAlign: "center", fontWeight: "bold" }}>
          Selected Seats: {selectedSeats.length ? selectedSeats.join(", ") : "None"}
        </p>

        <button
          onClick={handleBook}
          style={{
            width: "100%",
            padding: 14,
            background: "#27ae60",
            color: "#fff",
            fontWeight: "bold",
            border: "none",
            borderRadius: 8,
            cursor: "pointer",
            transition: "0.3s"
          }}
          onMouseOver={e => e.target.style.background = "#1e8449"}
          onMouseOut={e => e.target.style.background = "#27ae60"}
        >
          ✅ Confirm Booking ({selectedSeats.length} seat{selectedSeats.length !== 1 ? "s" : ""})
        </button>

        {msg && (
          <p style={{ textAlign: "center", color: msg.includes("successful") ? "green" : "red", marginTop: 16, fontWeight: "bold" }}>
            {msg}
          </p>
        )}
      </div>
    </div>
  );
}
