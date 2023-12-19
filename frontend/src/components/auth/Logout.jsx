import React, { useContext } from "react";
import { AuthContext } from "./AuthProvider";
import { Link, useNavigate } from "react-router-dom";

const Logout = () => {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleLogout = () => {
    navigate("/", { state: { message: " You have been logged out!" } });
    auth.handleLogout();
  };


  return (
    <button className="dropdown-item" onClick={handleLogout}>
      Logout
    </button>
  );
};

export default Logout;
