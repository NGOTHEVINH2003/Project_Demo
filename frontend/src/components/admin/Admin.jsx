// Admin.js

import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";
import RequireAuth from "../auth/RequireAuth";
import ExistingRooms from "../room/ExistingRooms";
import Bookings from "../booking/Bookings";
import ManageUser from "../user/ManageUser";
import NavBar from "../layout/NavBar"

const Admin = () => {
  const { user } = useAuth();
  const [selectedMenu, setSelectedMenu] = React.useState("rooms");

  // Fetch the user's role from local storage
  const storedUserRole = localStorage.getItem("userRole");

  const renderContent = () => {
    switch (selectedMenu) {
      case "rooms":
        return <ExistingRooms />;
      case "bookings":
        return <Bookings />;
      case "manage":
        return <ManageUser />;
      default:
        return null;
    }
  };

  return (
    <RequireAuth requiredRole="admin">
      <div className="container-fluid mt-5">
        <NavBar />
        <div className="row">
          <div className="col-md-2">
            <div className="card">
              <div className="card-body text-center">
                <h2 className="mb-4">Welcome to Admin Panel</h2>
                <hr />
                <ul className="list-group">
                  <li className={`list-group-item ${selectedMenu === "rooms" ? "active" : ""}`}>
                    <Link to="#" onClick={() => setSelectedMenu("rooms")} id="link-not-under" className="link-not-under">
                      Manage Rooms
                    </Link>
                  </li>
                  <li className={`list-group-item ${selectedMenu === "bookings" ? "active" : ""}`}>
                    <Link to="#" onClick={() => setSelectedMenu("bookings")} id="link-not-under" className="link-not-under">
                      Manage Bookings
                    </Link>
                  </li>
                  <li className={`list-group-item ${selectedMenu === "manage" ? "active" : ""}`}>
                    <Link to="#" onClick={() => setSelectedMenu("manage")} id="link-not-under" className="link-not-under">
                      Manage Users
                    </Link>
                  </li>
                </ul>
              </div>
            </div>
          </div>
          <div className="col-md-10">
            {renderContent()}
          </div>
        </div>
      </div>
    </RequireAuth>
  );
};

export default Admin;
