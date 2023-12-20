import React, { useState } from "react"
import { addRoom } from "../utils/ApiFunctions"
import RoomTypeSelector from "../common/RoomTypeSelector"
import { Link } from "react-router-dom"

const AddRoom = () => {
	const [newRoom, setNewRoom] = useState({
		img_url: null,
		roomType: "",
		price: "",
		roomId: "",
		floor: "",
		information: ""
	})

	const [successMessage, setSuccessMessage] = useState("")
	const [errorMessage, setErrorMessage] = useState("")
	const [imagePreview, setImagePreview] = useState("")

	const handleRoomInputChange = (e) => {
		const name = e.target.name
		let value = e.target.value

		setNewRoom({ ...newRoom, [name]: value })
	}

	const handleImageChange = (e) => {
		const selectedImage = e.target.files[0]
		setNewRoom({ ...newRoom, img_url: selectedImage })
		setImagePreview(URL.createObjectURL(selectedImage))
	}


	const handleSubmit = async (e) => {
		e.preventDefault();
		try {
			const success = await addRoom(
				newRoom.img_url,
				newRoom.roomType,
				newRoom.price,
				newRoom.roomId,
				newRoom.floor,
				newRoom.information
			);

			if (success !== undefined) {
				setSuccessMessage("A new room was added successfully!");
				setNewRoom({
					img_url: null,
					roomType: "",
					price: "",
					roomId: "",
					floor: "",
					information: "",
				});
				setImagePreview("");
				setErrorMessage("");
			} else {
				setErrorMessage("Error adding new room");
			}
		} catch (error) {
			setErrorMessage(error.message);
		}

		setTimeout(() => {
			setSuccessMessage("");
			setErrorMessage("");
		}, 3000);
	};

	// const handleSubmit = async (e) => {
	// 	e.preventDefault()
	// 	try {
	// 		const success = await addRoom(newRoom.img_url, newRoom.roomType, newRoom.price)
	// 		if (success !== undefined) {
	// 			setSuccessMessage("A new room was  added successfully !")
	// 			setNewRoom({ img_url: null, roomType: "", price: "" })
	// 			setImagePreview("")
	// 			setErrorMessage("")
	// 		} else {
	// 			setErrorMessage("Error adding new room")
	// 		}
	// 	} catch (error) {
	// 		setErrorMessage(error.message)
	// 	}
	// 	setTimeout(() => {
	// 		setSuccessMessage("")
	// 		setErrorMessage("")
	// 	}, 3000)
	// }

	return (
		<>
			<section className="container mt-5 mb-5">
				<div className="row justify-content-center">
					<div className="col-md-8 col-lg-6">
						<h2 className="mt-5 mb-2">Add a New Room</h2>
						{successMessage && (
							<div className="alert alert-success fade show"> {successMessage}</div>
						)}

						{errorMessage && <div className="alert alert-danger fade show"> {errorMessage}</div>}

						<form onSubmit={handleSubmit}>
							<div className="mb-3">
								<label htmlFor="roomType" className="form-label">
									Room Type
								</label>
								<div>
									<RoomTypeSelector
										handleRoomInputChange={handleRoomInputChange}
										newRoom={newRoom}
									/>
								</div>
							</div>
							<div className="mb-3">
								<label htmlFor="price" className="form-label">
									Room Price
								</label>
								<input
									required
									type="number"
									className="form-control"
									id="price"
									name="price"
									value={newRoom.price}
									onChange={handleRoomInputChange}
								/>
							</div>

							{/* ------------------- */}
							<div className="mb-3">
								<label htmlFor="roomId" className="form-label">
									Room ID
								</label>
								<input
									required
									type="text"
									className="form-control"
									id="roomId"
									name="roomId"
									value={newRoom.roomId}
									onChange={handleRoomInputChange}
								/>
							</div>

							<div className="mb-3">
								<label htmlFor="floor" className="form-label">
									Room Floor
								</label>
								<input
									required
									type="text"
									className="form-control"
									id="floor"
									name="floor"
									value={newRoom.floor}
									onChange={handleRoomInputChange}
								/>
							</div>

							<div className="mb-3">
								<label htmlFor="information" className="form-label">
									Room Information
								</label>
								<textarea
									required
									className="form-control"
									id="information"
									name="information"
									value={newRoom.information}
									onChange={handleRoomInputChange}
								/>
							</div>

							{/* ------------------- */}

							<div className="mb-3">
								<label htmlFor="img_url" className="form-label">
									Room img_url
								</label>
								<input
									required
									name="img_url"
									id="img_url"
									type="file"
									className="form-control"
									onChange={handleImageChange}
								/>
								{imagePreview && (
									<img
										src={imagePreview}
										alt="Preview  room img_url"
										style={{ maxWidth: "400px", maxHeight: "400px" }}
										className="mb-3"></img>
								)}
							</div>
							
							<div className="d-grid gap-2 d-md-flex mt-2">
								<Link to={"/existing-rooms"} className="btn btn-outline-info">
									Existing rooms
								</Link>
								<button type="submit" className="btn btn-outline-primary ml-5">
									Save Room
								</button>
							</div>
						</form>
					</div>
				</div>
			</section>
		</>
	)
}

export default AddRoom
