import React, { useEffect, useState } from "react";
import { API } from "../services/api";

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const [msg, setMsg] = useState("");
  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    if (!user) return;

    API.get(`/bookings/my/${user.id}`)
      .then((res) => setBookings(res.data))
      .catch((err) => {
        console.error(err);
        setMsg("Failed to load bookings.");
      });
  }, [user]);

  const handleCancel = (id) => {
    if (!window.confirm("Are you sure you want to cancel this booking?")) return;

    API.delete(`/bookings/cancel/${id}?userId=${user.id}`)
      .then((res) => {
        setBookings((prev) => prev.filter((b) => b.id !== id));
        alert(res.data);
      })
      .catch((err) => alert(err.response?.data?.message || "Error cancelling booking"));
  };

  const canCancel = (showTime) => {
    const showDate = new Date(showTime);
    return (showDate - new Date()) / (1000 * 60 * 60) > 3;
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h2>My Bookings</h2>
      {msg && <p style={{ color: "red" }}>{msg}</p>}
      <table style={{ width: "100%", borderCollapse: "collapse" }}>
        <thead>
          <tr>
            <th>Screen</th>
            <th>Show Time</th>
            <th>Seats</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {bookings.length === 0 ? (
            <tr>
              <td colSpan="4" style={{ textAlign: "center" }}>No bookings found</td>
            </tr>
          ) : (
            bookings.map((b) => (
              <tr key={b.id}>
                <td>{b.showRef?.screen ?? "N/A"}</td>
                <td>{b.showRef?.showTime?.replace("T", " ") ?? "N/A"}</td>
                <td>{b.seatNumbers.join(", ")}</td>
                <td>
                  <button
                    onClick={() => handleCancel(b.id)}
                    disabled={!canCancel(b.showRef?.showTime)}
                  >
                    Cancel
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}
