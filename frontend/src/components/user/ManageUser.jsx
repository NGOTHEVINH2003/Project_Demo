import React, { useEffect, useState } from "react";
import { getAllUsers, deleteUser } from "../utils/ApiFunctions";
import { Link } from "react-router-dom";
import { FaEdit, FaTrashAlt, FaSearch } from "react-icons/fa";

const ManageUser = () => {
  const [users, setUsers] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    setIsLoading(true);
    try {
      const result = await getAllUsers();
      setUsers(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
      setIsLoading(false);
    }
  };

  const handleDelete = async (userId) => {
    try {
      const result = await deleteUser(userId);
      if (result === "") {
        setSuccessMessage(`User with ID ${userId} was deleted`);
        setUsers(users.filter((user) => user.id !== userId));
      } else {
        console.error(`Error deleting user: ${result.message}`);
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  const handleSearch = (event) => {
    setSearchTerm(event.target.value);
  };

  const filteredUsers = users.filter((user) => {
    const email = `${user.email}`.toLowerCase();
    return email.includes(searchTerm.toLowerCase());
  });

  return (
    <>
      <div className="container col-md-8 col-lg-6">
        {successMessage && (
          <p className="alert alert-success mt-5">{successMessage}</p>
        )}

        {errorMessage && (
          <p className="alert alert-danger mt-5">{errorMessage}</p>
        )}
      </div>

      {isLoading ? (
        <p>Loading existing users</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
              <h2>Existing Users</h2>
            </div>

            <div className="container mt-3">
            <div className="input-group mb-3">
                <input
                  type="text"
                  placeholder="Search by email"
                  value={searchTerm}
                  onChange={handleSearch}
                  className="form-control"
                />
                <div className="input-group-append">
                  <span className="input-group-text" id="searchicon">
                    <FaSearch />
                  </span>
                </div>
              </div>
            </div>

            <table className="table table-bordered table-hover mt-3">
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>Username</th>
                  <th>Email</th>
                  <th>Role</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {filteredUsers.map((user) => (
                  <tr key={user.id} className="text-center">
                    <td>{user.id}</td>
                    <td>{user.firstName} {user.lastName}</td>
                    <td>{user.email}</td>
                    <td>{user.role.name}</td>
                    <td className="gap-2">
                      <Link to={`/edit-user/${user.id}`} className="gap-2">
                        <span className="btn btn-warning btn-sm ml-5">
                          <FaEdit />
                        </span>
                      </Link>
                      <button
                        className="btn btn-danger btn-sm ml-5"
                        onClick={() => handleDelete(user.id)}
                      >
                        <FaTrashAlt />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </section>
        </>
      )}
    </>
  );
};

export default ManageUser;
