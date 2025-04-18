import React, { useEffect, useState } from "react"
import { getRoomById, updateRoom } from "../utils/ApiFunctions"
import { Link, useParams } from "react-router-dom"

const EditRoom = () => {
	const [room, setRoom] = useState({
		img_url: "",
		roomType: "",
		price: "",
		roomId: "",
		floor: "",
		room_status: "",
		room_info: ""
	})

	const [imagePreview, setImagePreview] = useState("")
	const [successMessage, setSuccessMessage] = useState("")
	const [errorMessage, setErrorMessage] = useState("")
	const { roomId } = useParams()

	const handleImageChange = (e) => {
		const selectedImage = e.target.files[0]
		setRoom({ ...room, img_url: selectedImage })
		setImagePreview(URL.createObjectURL(selectedImage))
	}

	const handleInputChange = (event) => {
		const { name, value } = event.target
		setRoom({ ...room, [name]: value })
	}

	useEffect(() => {
		const fetchRoom = async () => {
			try {
				const roomData = await getRoomById(roomId)
				setRoom(roomData)
				setImagePreview(roomData.img_url)
			} catch (error) {
				console.error(error)
			}
		}

		fetchRoom()
	}, [roomId])

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			const response = await updateRoom(roomId, room);
			if (response.status === 200) {
				setSuccessMessage("Room updated successfully!");
				const updatedRoomData = await getRoomById(roomId);
				setRoom(updatedRoomData);
				setImagePreview(updatedRoomData.img_url);
				setErrorMessage("");
			} else {
				setErrorMessage("Error updating room");
			}
		} catch (error) {
			console.error(error);
			setErrorMessage(error.message);
		}
	};


	return (
		<div className="container mt-5 mb-5">
			<h3 className="text-center mb-5 mt-5">Edit Room</h3>
			<div className="row justify-content-center">
				<div className="col-md-8 col-lg-6">
					{successMessage && (
						<div className="alert alert-success" role="alert">
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
							<label htmlFor="roomType" className="form-label hotel-color">
								Room Type
							</label>
							<input
								type="text"
								className="form-control"
								id="roomType"
								name="roomType"
								value={room.roomType}
								onChange={handleInputChange}
							/>
						</div>
						<div className="mb-3">
							<label htmlFor="price" className="form-label hotel-color">
								Room Price
							</label>
							<input
								type="number"
								className="form-control"
								id="price"
								name="price"
								value={room.price}
								onChange={handleInputChange}
							/>
						</div>

						{/* --------------------------- */}

						<div className="mb-3">
							<label htmlFor="roomId" className="form-label hotel-color">
								Room ID
							</label>
							<input
								type="text"
								className="form-control"
								id="roomId"
								name="roomId"
								value={room.roomId}
								onChange={handleInputChange}
							/>
						</div>

						<div className="mb-3">
							<label htmlFor="floor" className="form-label hotel-color">
								Room Floor
							</label>
							<input
								type="text"
								className="form-control"
								id="floor"
								name="floor"
								value={room.floor}
								onChange={handleInputChange}
							/>
						</div>

						<div className="mb-3">
							<label htmlFor="room_status" className="form-label hotel-color">
								Room Status
							</label>
							<input
								type="text"
								className="form-control"
								id="room_status"
								name="room_status"
								value={room.room_status}
								onChange={handleInputChange}
							/>
						</div>

						<div className="mb-3">
							<label htmlFor="room_info" className="form-label hotel-color">
								Room Information
							</label>
							<textarea
								className="form-control"
								id="room_info"
								name="room_info"
								defaultValue={room.room_info}
								onChange={handleInputChange}
							/>
						</div>


						{/* --------------------------- */}
						<div className="mb-3">
							<label htmlFor="img_url" className="form-label hotel-color">
								img_url
							</label>
							<input
								required
								type="file"
								className="form-control"
								id="img_url"
								name="img_url"
								onChange={handleImageChange}
							/>
							{imagePreview && (
								<img
									src={imagePreview}
									alt="Room preview"
									style={{ maxWidth: "400px", maxHeight: "400" }}
									className="mt-3"
								/>
							)}
							
						</div>
						<div className="d-grid gap-2 d-md-flex mt-2">
							<Link to={"/admin"} className="btn btn-outline-info ml-5">
								back
							</Link>
							<button type="submit" className="btn btn-outline-warning">
								Edit Room
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	)
}
export default EditRoom
