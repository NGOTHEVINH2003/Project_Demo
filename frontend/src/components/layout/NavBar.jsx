import React, { useState } from "react";
import { NavLink } from "react-router-dom";
import Logout from "../auth/Logout";
import { AuthProvider } from "../auth/AuthProvider";

const NavBar = () => {
  const [showUserMenu, setShowUserMenu] = useState(false);

  const handleAccountClick = () => {
    setShowUserMenu(!showUserMenu);
  };

  const isLoggedIn = localStorage.getItem("token");
  const userRole = localStorage.getItem("userRole");
  const userName = localStorage.getItem("userId");

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-light">
      <div className="container">
        <NavLink to={"/"} className="navbar-brand">
          <img
            src={require("../assets/images/htLogo.png")}
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
          aria-expanded={showUserMenu ? "true" : "false"}
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


            <li className="nav-item">
              <NavLink className="nav-link" to={"/find-booking"}>
                Find Booking
              </NavLink>
            </li>
          </ul>

          {/* "Account" button on the right with custom margin */}
          <div className="navbar-nav" style={{ marginLeft: "auto" }}>
            <li className="nav-item dropdown">
              {isLoggedIn ? (
                <button
                  className={`btn btn-secondary dropdown-toggle`}
                  style={{ backgroundColor: "transparent", color: "black", border: "none" }}
                  onClick={handleAccountClick}
                >
                  {userName}
                </button>
              ) : (
                <NavLink className="nav-link" to={"/login"}>
                  Account
                </NavLink>
              )}
              <div
                className={`dropdown-menu ${showUserMenu ? "show" : ""}`}
                style={{
                  position: "absolute",
                  minWidth: "200px",
                  marginTop: "0.5rem",
                  left: "auto",
                  right: showUserMenu ? "0" : "-9999px",
                }}
              >
                {isLoggedIn ? (
                  <>
                    {userRole === "admin" && (
                      <>
                        <NavLink className="dropdown-item" to={"/admin"}>
                          Admin Panel
                        </NavLink>
                        <div className="dropdown-divider"></div>
                      </>
                    )}
                    <NavLink className="dropdown-item" to={"/profile"}>
                      Profile
                    </NavLink>
                    <div className="dropdown-divider"></div>
                    <AuthProvider><Logout /></AuthProvider>
                  </>
                ) : (
                  <NavLink className="dropdown-item" to={"/login"}>
                    Login
                  </NavLink>
                )}
              </div>
            </li>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
