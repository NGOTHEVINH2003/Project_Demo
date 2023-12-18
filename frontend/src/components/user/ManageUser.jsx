import React, { useEffect, useState } from "react";
import { getAllUsers, deleteUser, updateUserRole } from "../utils/ApiFunctions";
import { Link } from "react-router-dom";
import { FaEdit, FaTrashAlt } from "react-icons/fa";

const ManageUser = () => {
  const [users, setUsers] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

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

  const handleRoleChange = async (userId, newRole) => {
    try {
      const result = await updateUserRole(userId, newRole);
      if (result === "") {
        setSuccessMessage(`Role of User with ID ${userId} was updated`);
        fetchUsers();
      } else {
        console.error(`Error updating user role: ${result.message}`);
      }
    } catch (error) {
      setErrorMessage(error.message);
    }
    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  const grantAdminAccess = (userId) => {
    console.log(`Grant admin access to User with ID ${userId}`);
  };

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

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th>ID</th>
                  <th>Username</th>
                  <th>Role</th>
                  <th>Actions</th>
                </tr>
              </thead>

              <tbody>
                {users.map((user) => (
                  <tr key={user.id} className="text-center">
                    <td>{user.id}</td>
                    <td>{user.firstName} {user.lastName}</td>
                    <td>
                      <div className="btn-group">
                        <button
                          type="button"
                          className="btn btn-secondary btn-sm dropdown-toggle"
                          data-bs-toggle="dropdown"
                          aria-expanded="false"
                        >
                          {user.role.name}
                        </button>
                        {user.role.name === 'admin' && (
                          <ul className="dropdown-menu">
                            <li>
                              <button
                                className="dropdown-item"
                                onClick={() => handleRoleChange(user.id, 'user')}
                              >
                                Change to User
                              </button>
                            </li>
                          </ul>
                        )}
                        {user.role.name === 'user' && (
                          <ul className="dropdown-menu">
                            <li>
                              <button
                                className="dropdown-item"
                                onClick={() => handleRoleChange(user.id, 'admin')}
                              >
                                Change to Admin
                              </button>
                            </li>
                            <li>
                              <button
                                className="dropdown-item"
                                onClick={() => grantAdminAccess(user.id)}
                              >
                                Grant Admin Access
                              </button>
                            </li>
                          </ul>
                        )}
                      </div>
                    </td>
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
