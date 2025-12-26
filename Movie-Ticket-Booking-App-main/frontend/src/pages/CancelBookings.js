import React, { useEffect, useState } from "react";
import { API } from "../services/api";

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    if (!user) return;

    API.get(`/bookings/my/${user.id}`)
      .then((res) => setBookings(res.data))
      .catch((err) => console.error(err));
  }, [user]);

  const handleCancel = (id) => {
    if (window.confirm("Are you sure you want to cancel this booking?")) {
      API.delete(`/bookings/cancel/${id}?userId=${user.id}`)
        .then((res) => {
          alert(res.data);
          setBookings(bookings.filter((b) => b.id !== id));
        })
        .catch((err) => alert("Error cancelling booking"));
    }
  };

  const canCancel = (showTime) => {
    const showDate = new Date(showTime);
    const now = new Date();
    const diffHours = (showDate - now) / (1000 * 60 * 60);
    return diffHours > 3;
  };

  return (
    <div style={{ padding: "2rem" }}>
      <h2>My Bookings</h2>
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
          {bookings.map((b) => (
            <tr key={b.id}>
              <td>{b.showRef?.screen}</td>
              <td>{b.showRef?.showTime.replace("T", " ")}</td>
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
          ))}
          {bookings.length === 0 && (
            <tr>
              <td colSpan="4">No bookings found</td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
}
