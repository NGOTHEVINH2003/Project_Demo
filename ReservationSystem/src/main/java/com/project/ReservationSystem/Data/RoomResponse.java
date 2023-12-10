package com.project.ReservationSystem.Data;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.tomcat.util.codec.binary.Base64;
import java.util.List;

@Data
@NoArgsConstructor
public class RoomResponse {
    private int id;
    private String roomType;
    private float Price;
    private boolean isBooked;
    private String photo;
    private List<BookingResponse>bookings;

    public RoomResponse(int id,String roomType,float Price){
        this.id = id;
        this.roomType = roomType;
        this.Price = Price;
    }
    public RoomResponse(int id, String roomType, float Price, boolean isBooked, String photo, List<BookingResponse> bookings){
        this.id = id;
        this.roomType = roomType;
        this.Price = Price;
        this.isBooked = isBooked;
        this.photo = photo;
        this.bookings = bookings;
    }
}
