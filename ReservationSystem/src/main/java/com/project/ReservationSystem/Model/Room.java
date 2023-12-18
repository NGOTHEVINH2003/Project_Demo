package com.project.ReservationSystem.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String RoomType;
    private float Price;
    private boolean isBooked = false;
    private String img_url;
    private int floor;
    private int RoomId;
    private String roomInfo;
    private String roomStatus;


    public Room(String roomType, float price, boolean isBooked, String img_url, int floor,int roomId,String roomStatus) {
        RoomType = roomType;
        Price = price;
        this.isBooked = isBooked;
        this.img_url = img_url;
        this.floor = floor;
        this.RoomId = roomId;
        this.roomStatus = roomStatus;
    }

    public Room(String roomType, float price, String address) {
        RoomType = roomType;
        Price = price;
        this.img_url = img_url;

    }

}
