package com.paololauria.bnb.dtos;

import com.paololauria.bnb.model.entities.Room;

import java.math.BigDecimal;

public class RoomDetailsDto {
    private Long roomId;
    private String roomName;
    private String roomType;
    private BigDecimal pricePerNight;
    private String roomCover;
    private String description;

    public RoomDetailsDto(){

    }

    public RoomDetailsDto(Room room){
        roomId = room.getRoomId();
        roomName = room.getRoomName();
        roomType = room.getRoomType();
        pricePerNight = room.getPricePerNight();
        roomCover = room.getRoomCover();
        description = room.getDescription();
    }


    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public String getRoomCover() {
        return roomCover;
    }

    public void setRoomCover(String roomCover) {
        this.roomCover = roomCover;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
