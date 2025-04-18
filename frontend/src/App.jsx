import React from "react";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "/node_modules/bootstrap/dist/js/bootstrap.min.js";
import ExistingRooms from "./components/room/ExistingRooms";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/home/Home";
import EditRoom from "./components/room/EditRoom";
import AddRoom from "./components/room/AddRoom";
import NavBar from "./components/layout/NavBar";
import Footer from "./components/layout/Footer";
import RoomListing from "./components/room/RoomListing";
import Admin from "./components/admin/Admin";
import Checkout from "./components/booking/Checkout";
import BookingSuccess from "./components/booking/BookingSuccess";
import Bookings from "./components/booking/Bookings";
import FindBooking from "./components/booking/FindBooking";
import Login from "./components/auth/Login";
import Registration from "./components/auth/Registration";
import Profile from "./components/auth/Profile";
import { AuthProvider } from "./components/auth/AuthProvider";
import RequireAuth from "./components/auth/RequireAuth";
import EditUser from "./components/user/EditUser";

function App() {
  return (
    <main>
      <Router>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route
            path="/edit-room/:roomId"
            element={
              <RequireAuth requiredRole="admin">
                <NavBar />
                <EditRoom />
              </RequireAuth>
            }
          />
          <Route
            path="/edit-user/:userId"
            element={
              <RequireAuth>
                <NavBar />
                <EditUser />
              </RequireAuth>
            }
          />
          <Route path="/existing-rooms" element={<ExistingRooms />} />
          <Route
            path="/add-room"
            element={
              <RequireAuth requiredRole="admin">
                <NavBar />
                <AddRoom />
              </RequireAuth>
            }
          />
          <Route
            path="/book-room/:roomId"
            element={
              <RequireAuth>
                <NavBar />
                <Checkout />
              </RequireAuth>
            }
          />
          <Route path="/browse-all-rooms" element={<><NavBar /><RoomListing /></>} />
          <Route
            path="/admin"
            element={
              <RequireAuth requiredRole="admin">
                <Admin />
              </RequireAuth>
            }
          />
          <Route path="/booking-success" element={<>
            <NavBar />
            <BookingSuccess />
          </>} />
          <Route path="/existing-bookings" element={<Bookings />} />
          <Route path="/find-booking"
            element={
            <RequireAuth>
              <FindBooking />
            </RequireAuth>
          } />

          {/* Wrap the Login component with AuthProvider */}
          <Route
            path="/login"
            element={
              <AuthProvider>
                <Login />
              </AuthProvider>
            }
          />


          <Route path="/login" element={<AuthProvider><Login /></AuthProvider>} />
          <Route path="/register" element={<Registration />} />

          <Route path="/profile" element={<Profile />} />
          <Route path="/logout" element={<FindBooking />} />
        </Routes>
      </Router>
      <Footer />
    </main>

  );
}

export default App;
