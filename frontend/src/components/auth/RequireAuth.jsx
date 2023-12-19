// RequireAuth.js

import React from "react";
import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "./AuthProvider";

const RequireAuth = ({ children, requiredRole }) => {
  const user = localStorage.getItem("token");
  const location = useLocation();

  // Check the user's role from local storage
  const storedUserRole = localStorage.getItem("userRole");

  if (!user || !storedUserRole) {
    // Redirect to login if not authenticated or role not found
    return <Navigate to="/login" state={{ path: location.pathname }} />;
  }

  if (requiredRole && storedUserRole !== requiredRole) {
    // Render an error message if the required role is not met
    return (
      <div>
        <p>You do not have permission to access this feature.</p>
        <p>Please log in with the correct account or contact an administrator.</p>
      </div>
    );
  }

  return children;
};

export default RequireAuth;
