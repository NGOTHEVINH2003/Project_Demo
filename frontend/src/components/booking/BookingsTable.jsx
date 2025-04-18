import { parseISO, format} from "date-fns"
import React, { useState, useEffect } from "react"
import DateSlider from "../common/DateSlider"

const BookingsTable = ({ bookingInfo, handleBookingCancellation }) => {
	const [filteredBookings, setFilteredBookings] = useState(bookingInfo)

	const filterBookings = (startDate, endDate) => {
		let filtered = bookingInfo
		if (startDate && endDate) {
			filtered = bookingInfo.filter((booking) => {
				const bookingStartDate = parseISO(booking.checkIn)
				const bookingEndDate = parseISO(booking.checkOut)
				return (
					bookingStartDate >= startDate && bookingEndDate <= endDate && bookingEndDate > startDate
				)
			})
		}
		setFilteredBookings(filtered)
	}

	useEffect(() => {
		setFilteredBookings(bookingInfo)
	}, [bookingInfo])

	return (
		<div className="container-fluid">
			<section className="p-4">
				<DateSlider onDateChange={filterBookings} onFilterChange={filterBookings} />

				{filteredBookings.length > 0 ? (
					<table className="table table-bordered table-hover shadow">
						<thead>
							<tr>
								<th>S/N</th>
								<th>Booking ID</th>
								<th>Room ID</th>
								<th>Room Type</th>
								<th>Check-In Date</th>
								<th>Check-Out Date</th>
								<th>Guest Name</th>
								<th>Guest Email</th>
								<th>Adults</th>
								<th>Children</th>
								<th>Total Guest</th>
								<th>Confirmation Code</th>
								<th colSpan={2}>Actions</th>
							</tr>
						</thead>

						<tbody className="text-center">
							{filteredBookings.map((booking, index) => (
								<tr key={booking.bookingId}>
									<td>{index + 1}</td>
									<td>{booking.bookingId}</td>
									<td>{booking.room.id}</td>
									<td>{booking.room.roomType}</td>
									<td>{format(new Date(booking.checkIn), 'dd-MM-yyyy')}</td>
									<td>{format(new Date(booking.checkOut), 'dd-MM-yyyy')}</td>
									<td>{booking.guestFullName}</td>
									<td>{booking.guestEmail}</td>
									<td>{booking.numOfAdult}</td>
									<td>{booking.numOfChildren}</td>
									<td>{booking.totalGuest}</td>
									<td>{booking.confirmationCode}</td>
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
					<p>No booking found for the selected dates</p>
				)}

			</section>
		</div>
	)
}

export default BookingsTable
