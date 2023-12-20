
import React, { useEffect, useState } from "react"
import { getAllRooms } from "../utils/ApiFunctions"
import RoomCard from "./RoomCard"
import { Col, Container, Row } from "react-bootstrap"
import RoomFilter from "../common/RoomFilter"
import RoomFilter2 from "../common/RoomFilter2"

import RoomPaginator from "../common/RoomPaginator"

const NavBar = () => {
  const [showUserMenu, setShowUserMenu] = useState(false);

  const handleAccountClick = () => {
    setShowUserMenu(!showUserMenu);
  };

  // Variables to check if the user is logged in and retrieve user-related data
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

        {/* Toggler button for mobile navigation */}
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


	return (
		<Container>
			<Row>
				<Col md={4} className="mb-3 mb-md-0">
					<RoomFilter data={data} setFilteredData={setFilteredData} />
				</Col>

				<Col md={4} className="mb-3 mb-md-0">
					<RoomFilter2 data={data} setFilteredData={setFilteredData} />
				</Col>

				<Col md={4} className="d-flex align-items-center justify-content-end">
					<RoomPaginator
						currentPage={currentPage}
						totalPages={totalPages}
						onPageChange={handlePageChange}
					/>
				</Col>
			</Row>

          {/* "Account" button on the right with custom margin */}
          <div className="navbar-nav ml-auto">
            <li className="nav-item dropdown">
              {isLoggedIn ? (
                <button
                  className={`btn btn-secondary dropdown-toggle`}
                  style={{
                    backgroundColor: "transparent",
                    color: "black",
                    border: "none",
                  }}
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
                  right: showUserMenu ? "0" : "-9999px",
                  minWidth: "100%",
                  marginTop: "0.5rem",
                  left: 0,
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
                    <AuthProvider>
                      <Logout />
                    </AuthProvider>
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