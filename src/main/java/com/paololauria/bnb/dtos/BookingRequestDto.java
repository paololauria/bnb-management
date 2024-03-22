package com.paololauria.bnb.dtos;

import com.paololauria.bnb.model.entities.Booking;

import java.time.LocalDate;

public class BookingRequestDto {
    private Long roomId;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public BookingRequestDto() {}

    public BookingRequestDto(Booking booking) {
        this.roomId = booking.getRoom().getRoomId();
        this.userId = booking.getUser().getId();
        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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