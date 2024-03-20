package com.paololauria.bnb.dtos;

import java.time.LocalDate;

public class RoomAvailabilityDto {
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public RoomAvailabilityDto() {
    }

    public RoomAvailabilityDto(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
}
