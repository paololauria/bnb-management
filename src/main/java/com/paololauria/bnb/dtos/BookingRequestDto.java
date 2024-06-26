package com.paololauria.bnb.dtos;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;

import java.time.LocalDate;

public class BookingRequestDto {
    private Long bookingId;
    private String roomName;
    private Long roomId;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String roomCover;
    private Double totalPrice;
    private Integer maxGuestRoom;

    public BookingRequestDto() {}

    public BookingRequestDto(Booking booking) {
        this.bookingId = booking.getBookingId();
        this.roomId = booking.getRoom().getRoomId();
        this.roomName = booking.getRoom().getRoomName();
        this.userId = booking.getUser().getId();
        this.checkInDate = booking.getCheckInDate();
        this.checkOutDate = booking.getCheckOutDate();
        this.roomCover = booking.getRoomCover();
        this.totalPrice = booking.getTotalPrice().doubleValue();
        this.maxGuestRoom = booking.getRoom().getMaxGuest();
    }

    public Booking fromDto(){
        Room room = new Room();
        room.setRoomId(this.roomId);

        User user = new User();
        user.setId(this.userId);

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
        booking.setCheckInDate(this.checkInDate);
        booking.setCheckOutDate(this.checkOutDate);
        return booking;
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

    public String getRoomCover() {
        return roomCover;
    }

    public void setRoomCover(String roomCover) {
        this.roomCover = roomCover;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getMaxGuestRoom() {
        return maxGuestRoom;
    }

    public void setMaxGuestRoom(Integer maxGuestRoom) {
        this.maxGuestRoom = maxGuestRoom;
    }
}