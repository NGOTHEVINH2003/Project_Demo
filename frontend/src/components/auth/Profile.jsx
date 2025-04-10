import React, { useEffect, useState } from "react"
import { deleteUser, getBookingsByUserId, getUser, cancelBooking } from "../utils/ApiFunctions"
import { useNavigate } from "react-router-dom"
import NavBar from "../layout/NavBar"

const Profile = () => {
	const [user, setUser] = useState({
		id: "",
		email: "",
		firstName: "",
		lastName: "",
		role: [{ id: "", name: "" }]
	})

	const [bookings, setBookings] = useState([
		{
			bookingId: "",
			room: { id: "", roomType: "" },
			checkIn: "",
			checkOut: "",
			confirmationCode: ""
		}
	])
	const [message, setMessage] = useState("")
	const [errorMessage, setErrorMessage] = useState("")
	const navigate = useNavigate()
	const userId = localStorage.getItem("userId")
	const token = localStorage.getItem("token")

	useEffect(() => {
		const fetchUser = async () => {
			try {
				const userData = await getUser(userId, token);
				setUser(userData);
			} catch (error) {
				console.error("Error fetching user:", error);
			}
		};

		if (userId) {
			fetchUser();
		}
	}, [userId]);

	useEffect(() => {
		const fetchBookings = async () => {
			try {
				const response = await getBookingsByUserId(userId, token)
				setBookings(response)
			} catch (error) {
				console.error("Error fetching bookings:", error.message)
				setErrorMessage(error.message)
			}
		}

		fetchBookings()
	}, [userId])

	const handleDeleteAccount = async () => {
		const confirmed = window.confirm(
			"Are you sure you want to delete your account? This action cannot be undone."
		)
		if (confirmed) {
			await deleteUser(userId)
				.then((response) => {
					setMessage(response.data)
					localStorage.removeItem("token")
					localStorage.removeItem("userId")
					localStorage.removeItem("userRole")
					navigate("/")
					window.location.reload()
				})
				.catch((error) => {
					setErrorMessage(error.data)
				})
		}
	}
	
	const handleBookingCancellation = async (bookingId) => {
		try {
			await cancelBooking(bookingId)
			const data = await getBookingsByUserId(userId,token)
			setBookings(data)
		} catch (error) {
			console.log(errorMessage)
		}
	}


	return (
		<div className="container">
			<NavBar />
			{errorMessage && <p className="text-danger">{errorMessage}</p>}
			{message && <p className="text-danger">{message}</p>}
			{user ? (
				<div className="card p-5 mt-5" style={{ backgroundColor: "whitesmoke" }}>
					<h4 className="card-title text-center">User Information</h4>
					<div className="card-body">
						<div className="col-md-10 mx-auto">
							<div className="card mb-3 shadow">
								<div className="row g-0">
									<div className="col-md-2">
										<div className="d-flex justify-content-center align-items-center mb-4">
											<img
												src="https://themindfulaimanifesto.org/wp-content/uploads/2020/09/male-placeholder-image.jpeg"
												alt="Profile"
												className="rounded-circle"
												style={{ width: "150px", height: "150px", objectFit: "cover" }}
											/>
										</div>
									</div>

									<div className="col-md-10">
										<div className="card-body">
											<div className="form-group row">
												<label className="col-md-2 col-form-label fw-bold">ID:</label>
												<div className="col-md-10">
													<p className="card-text">{user.id}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row">
												<label className="col-md-2 col-form-label fw-bold">First Name:</label>
												<div className="col-md-10">
													<p className="card-text">{user.firstName}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row">
												<label className="col-md-2 col-form-label fw-bold">Last Name:</label>
												<div className="col-md-10">
													<p className="card-text">{user.lastName}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row">
												<label className="col-md-2 col-form-label fw-bold">Email:</label>
												<div className="col-md-10">
													<p className="card-text">{user.email}</p>
												</div>
											</div>
											<hr />

											<div className="form-group row">
												<label className="col-md-2 col-form-label fw-bold">Role:</label>
												<div className="col-md-10">
													<ul className="list-unstyled">
														<li key={user.role.id} className="card-text">
															{user.role.name}
														</li>
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div className="d-flex justify-content-center">
									<div className="mx-2 mb-4">
										<button className="btn btn-danger btn-sm" onClick={handleDeleteAccount}>
											Close account
										</button>
									</div>
								</div>
							</div>

							<h4 className="card-title text-center">Booking History</h4>

							{bookings.length > 0 ? (
								<table className="table table-bordered table-hover shadow">
									<thead>
										<tr>
											<th scope="col">Booking ID</th>
											<th scope="col">Room ID</th>
											<th scope="col">Room Type</th>
											<th scope="col">Check In Date</th>
											<th scope="col">Check Out Date</th>
											<th scope="col">Confirmation Code</th>
											<th scope="col">Status</th>
											<th scope="col">Action</th>
										</tr>
									</thead>
									<tbody>
										{bookings.map((booking, index) => (
											<tr key={index}>
												<td>{booking.bookingId}</td>
												<td>{booking.room.id}</td>
												<td>{booking.room.roomType}</td>
												<td>
													{booking.checkIn.at(2)}-{booking.checkIn.at(1)}-{booking.checkIn.at(0)}
												</td>
												<td>
													{booking.checkOut.at(2)}-{booking.checkOut.at(1)}-{booking.checkOut.at(0)}
												</td>
												<td>{booking.confirmationCode}</td>
												<td className="text-success">On-going</td>
												<td>
													<button
														className="btn btn-danger btn-sm"
														onClick={() => handleBookingCancellation(booking.bookingId)}>
														Cancel
													</button>
												</td>
											</tr>
										))}
									</tbody>
								</table>
							) : (
								<div className="text-center">
									<h1>You Haven't Make any reservation</h1>
								</div>
							)}


						</div>
					</div>
				</div>
			) : (
				<p>Loading user data...</p>
			)}
		</div>
	)
}

export default Profile
