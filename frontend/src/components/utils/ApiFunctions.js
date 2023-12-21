import axios from "axios"

export const api = axios.create({
	baseURL: "http://localhost:8080"
})

export const getHeader = () => {
	const token = localStorage.getItem("token")
	return {
		Authorization: `Bearer ${token}`,
		"Content-Type": "application/json"
	}
}

/* This function adds a new room room to the database */
export async function addRoom(img_url, roomType, price, roomId, floor, information) {
	const formData = new FormData()
	formData.append("img_url:", img_url);
	formData.append("roomType", roomType);
	formData.append("price", price);
	formData.append("roomId", roomId);
	formData.append("floor", floor);
	formData.append("information", information);

	const response = await api.post("/room/add", formData, {
		// headers: getHeader()
		"Content-Type": "multipart/form-data"
	})
	if (response.status === 201) {
		return true
	} else {
		return false
	}
}

/* This function gets all room types from thee database */
export async function getRoomTypes() {
	try {
		const response = await api.get("/room/type")
		return response.data
	} catch (error) {
		throw new Error("Error fetching room types")
	}
}
/* This function gets all rooms from the database */
export async function getAllRooms() {
	try {
		const result = await api.get("/room/all")
		return result.data
	} catch (error) {
		throw new Error("Error fetching rooms")
	}
}

/* This function gets list rooms after soft by roomid from the database */
export async function getSortedRooms() {
	try {
		const result = await api.get("/room/sortbyroomid")
		return result.data
	} catch (error) {
		throw new Error("Error fetching rooms")
	}
}


/* This function deletes a room by the Id */
export async function deleteRoom(roomId) {
	try {
		const result = await api.delete(`/room/delete/${roomId}`, {
			headers: getHeader()
		})
		window.location.reload();
		return result.data
	} catch (error) {
		throw new Error(`Error deleting room ${error.message}`)
	}
}
/* This function update a room */
export async function updateRoom(roomId, roomData) {
	const formData = new FormData()
	formData.append("img_url", roomData.img_url);
	formData.append("roomType", roomData.roomType);
	formData.append("price", roomData.price);
	formData.append("roomId", roomData.roomId);
	formData.append("floor", roomData.floor);
	formData.append("room_status", roomData.room_status);
	formData.append("room_info", roomData.room_info);
	const response = await api.put(`/room/update/${roomId}`, formData, {
		"Content-Type": "multipart/form-data"
	})
	return response
}


/* This funcction gets a room by the id */
export async function getRoomById(roomId) {
	try {
		const result = await api.get(`/room/getroom/${roomId}`)
		return result.data
	} catch (error) {
		throw new Error(`Error fetching room ${error.message}`)
	}
}
export async function getUserById(userId) {
	try {
		const result = await api.get(`/user/getuser/${userId}`)
		return result.data
	} catch (error) {
		throw new Error(`Error fetching room ${error.message}`)
	}
}

/* This function saves a new booking to the databse */
export async function bookRoom(booking) {
	try {
		const response = await api.post(`/booking/SaveBooking`, booking)
		return response.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error booking room : ${error.message}`)
		}
	}
}

/* This function gets alll bokings from the database */
export async function getAllBookings() {
	try {
		const result = await api.get("/booking/ViewAllBooking", {
			headers: getHeader()
		})
		return result.data
	} catch (error) {
		throw new Error(`Error fetching bookings : ${error.message}`)
	}
}

/* This function get booking by the cnfirmation code */
export async function getBookingByConfirmationCode(confirmationCode) {
	try {
		const result = await api.get(`/booking/searchByComfirmationCode/${confirmationCode}`)
		return result.data
	} catch (error) {
		if (error.response && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`Error find booking : ${error.message}`)
		}
	}
}

/* This is the function to cancel user booking */
export async function cancelBooking(bookingId) {
	try {
		const result = await api.get(`/booking/CancelBooking/${bookingId}`)
		return result.data
	} catch (error) {
		throw new Error(`Error cancelling booking :${error.message}`)
	}
}

/* This function gets all availavle rooms from the database with a given date and a room type */
export async function getAvailableRooms(checkInDate, checkOutDate, roomType) {
	const result = await api.get(
		`room/search?checkInDate=${checkInDate}
		&checkOutDate=${checkOutDate}&roomType=${roomType}`
	)
	return result
}

/* This function register a new user */
export async function registerUser(registration) {
	try {
		const response = await api.post("/auth/register", registration)
		return response.data
	} catch (error) {
		if (error.reeponse && error.response.data) {
			throw new Error(error.response.data)
		} else {
			throw new Error(`User registration error : ${error.message}`)
		}
	}
}

/* This function login a registered user */
export async function loginUser(login) {
	try {
		const response = await api.post("/auth/login", login)

		if (response.data != null) {
			if (response.status >= 200 && response.status < 300) {
				return response.data
			} else {
				return null
			}
		} else {
			console.log("Empty response or data not found!");
			return null; // Handle empty response or missing data
		}
	} catch (error) {
		console.error(error)
		return null
	}
}

/*  This is function to get the user profile */
export async function getUserProfile(userId, token) {
	try {
		const response = await api.get(`users/profile/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		throw error
	}
}

/* This isthe function to delete a user */
export async function deleteUser(userId) {
	try {
		const response = await api.post(`/user/delete/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		return error.message
	}
}

/* This is the function to get a single user */
export async function getUser(userId, token) {
	try {
		const response = await api.get(`/user/profile/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		throw error
	}
}

/* This is the function to get all users */
export async function getAllUsers(token) {
	try {
		const response = await api.get('/user/all', {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		throw error
	}
}

/* This is the function to get user bookings by the user id */
export async function getBookingsByUserId(userId, token) {
	try {
		const response = await api.get(`/booking/searchByMail/${userId}`, {
			headers: getHeader()
		})
		return response.data
	} catch (error) {
		console.error("Error fetching bookings:", error.message)
		throw new Error("Failed to fetch bookings")
	}
}

/**
 * New api
 * 
 * 
 * Update the role of a user.
 
 */
export async function updateUser(userId, newRole) {
	try {

		const response = await api.patch(
			`/user/updateRole/${userId}`, newRole
		);

		return response.data;
	} catch (error) {
		throw error;
	}
}



