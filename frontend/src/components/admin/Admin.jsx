import React from "react";
import { Link, Navigate } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";
import RequireAuth from "../auth/RequireAuth";
import ExistingRooms from "../room/ExistingRooms";
import Bookings from "../booking/Bookings";
import ManageUser from "../user/ManageUser";

const Admin = () => {
  const { user } = useAuth();
  const [selectedMenu, setSelectedMenu] = React.useState("rooms");

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
      <RequireAuth>
        <div className="container mt-5">
          <div className="row">
            <div className="col-md-3">
              <div className="card">
                <div className="card-body text-center">
                  <h2 className="mb-4">Welcome to Admin Panel</h2>
                  <hr />
                  <ul className="list-group">
                    <li className={`list-group-item ${selectedMenu === "rooms" ? "active" : ""}`}>
                      <Link to="#" onClick={() => setSelectedMenu("rooms")}>
                        Manage Rooms
                      </Link>
                    </li>
                    <li className={`list-group-item ${selectedMenu === "bookings" ? "active" : ""}`}>
                      <Link to="#" onClick={() => setSelectedMenu("bookings")}>
                        Manage Bookings
                      </Link>
                    </li>
                    <li className={`list-group-item ${selectedMenu === "manage" ? "active" : ""}`}>
                      <Link to="#" onClick={() => setSelectedMenu("manage")}>
                        Manage Users
                      </Link>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <div className="col-md-9">
              {renderContent()}
            </div>
          </div>
        </div>
      </RequireAuth>
    );
 
};

export default Admin;
