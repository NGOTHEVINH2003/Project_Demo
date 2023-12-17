import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import Logout from "../auth/Logout";
import { AuthProvider } from "../auth/AuthProvider";
import htLogo from "../assets/images/htLogo.png"
const NavBar = () => {
  const [showAccount, setShowAccount] = useState(false);

  const handleAccountClick = () => {
    setShowAccount(!showAccount);
  };

  const isLoggedIn = localStorage.getItem("token");
  const userRole = localStorage.getItem("userRole");

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <NavLink to={"/"} className="navbar-brand">
          <img
            src={htLogo}
            alt="Hotel Icon"
            style={{ maxHeight: "40px", marginRight: "5px" }}
          />
          <span className="hotel-color">Hotel</span>
        </NavLink>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNav"
          aria-controls="navbarNav"
          aria-expanded={showAccount ? "true" : "false"}
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarNav">
          <ul className="navbar-nav">
            {/* Left-side navigation items */}
            <li className="nav-item">
              <NavLink className="nav-link" to={"/browse-all-rooms"}>
                Browse Rooms
              </NavLink>
            </li>

            {isLoggedIn && userRole === "admin" && (
              <li className="nav-item">
                <NavLink className="nav-link" to={"/admin"}>
                  Admin
                </NavLink>
              </li>
            )}

            <li className="nav-item">
              <NavLink className="nav-link" to={"/find-booking"}>
                Find Booking
              </NavLink>
            </li>

            <li className="nav-item">
              <NavLink className="nav-link" to={"/about-us"}>
                About Us
              </NavLink>
            </li>

            <li className="nav-item">
              <NavLink className="nav-link" to={"/contact-us"}>
                Contact Us
              </NavLink>
            </li>
          </ul>

          <ul className="navbar-nav ml-auto"> {/* Right-side navigation items */}
            <li className="nav-item">
              <button
                className={`btn btn-secondary ${showAccount ? "active" : ""}`}
                style={{ backgroundColor: "purple", color: "white" }}
                onClick={handleAccountClick}
              >
                Account
              </button>
              <div
                className={`dropdown-menu dropdown-menu-right ${showAccount ? "show" : ""}`}
              >
                {isLoggedIn ? (
                  <AuthProvider><Logout/></AuthProvider>
                ) : (
                  <NavLink className="dropdown-item" to={"/login"}>
                    Login
                  </NavLink>
                )}
              </div>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
