import React from "react"
import { Link, useLocation } from "react-router-dom"
import Header from "../common/Header"

const BookingSuccess = () => {
	const location = useLocation()
	const message = location.state?.message
	const error = location.state?.error
	return (
		<div className="container">
			<Header title="Booking Success" />
			<div className="mt-5">
				{message ? (
					<div>
						<h1 className="text-success">{message}</h1>
					</div>
				) : (
					<div>
						<h3 className="text-danger"> Error Booking Room!</h3>
						<h1 className="text-danger text-center">{error}</h1>
					</div>
				)}
			</div>
		</div>
	)
}

export default BookingSuccess
