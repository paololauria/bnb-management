package com.paololauria.bnb.services.abstraction;

import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;

import java.time.LocalDate;

public interface BookingService {
    Booking makeBooking(Long roomId, String guestName,
                        LocalDate checkInDate, LocalDate checkOutDate);
    void updateRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate, boolean isAvailable);

    boolean checkAvailability(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

}
