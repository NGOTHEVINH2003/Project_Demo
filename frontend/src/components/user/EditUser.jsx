import React, { useEffect, useState } from "react";
import { getUserById, updateUser } from "../utils/ApiFunctions";
import { Link, useParams } from "react-router-dom";

const EditUser = () => {
  const [User, setUser] = useState({
    firstName: "",
    lastName: "",
    newRole: "",
  });

  const [newRole, setNewRole] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { userId } = useParams();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getUserById(userId);
        setUser(userData);
      } catch (error) {
        console.error(error);
        setErrorMessage("Error fetching user details");
      }
    };
    fetchUser();
  }, [userId]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    if (name === "newRole") {
      setNewRole(value); // Update newRole state with the selected value
    } else {
      setUser({ ...User, [name]: value }); // Update User state for other fields
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const result = await updateUser(userId, newRole);
      if (result) {
        const userData = await getUserById(userId)
        setSuccessMessage(`Role of User with ID ${userId} was updated`);
        setUser(userData);
        setErrorMessage("");
      } else {
        setErrorMessage(`Error updating user role: ${result.message}`);
      }
    } catch (error) {
      console.error(error);
      setErrorMessage(error.message);
    }
  };

  return (
    <div className="container mt-5 mb-5">
      <h3 className="text-center mb-5 mt-5">Edit User</h3>
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          {successMessage && (
            <div className="mt-3 alert alert-success" role="alert">
              {successMessage}
            </div>
          )}
          {errorMessage && (
            <div className="alert alert-danger" role="alert">
              {errorMessage}
            </div>
          )}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="firstName" className="form-label">
                First Name
              </label>
              <input
                type="text"
                className="form-control"
                id="firstName"
                name="firstName"
                value={User.firstName}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="lastName" className="form-label">
                Last Name
              </label>
              <input
                type="text"
                className="form-control"
                id="lastName"
                name="lastName"
                value={User.lastName}
                onChange={handleInputChange}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="role" className="form-label">
                Role
              </label>
              <select
                className="form-select"
                id="newRole"
                name="newRole"
                value={User.newRole}
                onChange={handleInputChange}
              >
                <option value="">Select Role</option>
                <option value="user" selected={User.newRole === "user"}>
                  User
                </option>
                <option value="admin" selected={User.newRole === "admin"}>
                  Admin
                </option>
              </select>
            </div>
            <div className="d-grid gap-2 d-md-flex mt-2">
              <Link to={"/admin"} className="btn btn-outline-info ml-5">
                Back
              </Link>
              <button type="submit" className="btn btn-outline-warning">
                Update
              </button>
            </div>
          </form>

        </div>
      </div>
    </div>
  );
};

export default EditUser;