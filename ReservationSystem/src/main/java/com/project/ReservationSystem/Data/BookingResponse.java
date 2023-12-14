package com.project.ReservationSystem.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private  int BookingId;
    private LocalDate CheckIn;
    private LocalDate CheckOut;
    private String guestFullName;
    private String guestEmail;
    private int numOfAdult;
    private int numOfChildren;
    private int totalGuest;
    private String ConfirmationCode;
    private RoomResponse room;

    public BookingResponse(Long id, LocalDate CheckIn, LocalDate CheckOut, String ConfirmationCode){
        this.BookingId = BookingId;
        this.CheckIn = CheckIn;
        this.CheckOut = CheckOut;
        this.ConfirmationCode = ConfirmationCode;
    }

}
