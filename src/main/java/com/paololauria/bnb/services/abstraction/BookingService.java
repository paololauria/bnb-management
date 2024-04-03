package com.paololauria.bnb.services.abstraction;

import com.paololauria.bnb.dtos.RoomAvailabilityDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking makeBooking(Long roomId, Long userId,
                        LocalDate checkInDate, LocalDate checkOutDate);
    void updateRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate, boolean isAvailable);
    boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate);
    List<RoomAvailabilityDto> getBookedDates();

    boolean isCancellationAllowed(Long bookingId);
    void cancelBooking(Long bookingId);
    boolean canReview(Long userId, Long roomId);

    List<Booking> getUserBookings(Long userId);
    Booking findById(Long id);


}
