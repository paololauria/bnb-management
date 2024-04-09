package com.paololauria.bnb.services.abstraction;

import com.paololauria.bnb.dtos.RoomAvailabilityDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.model.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking makeBooking(Long roomId, Long userId,
                        LocalDate checkInDate, LocalDate checkOutDate);
    Booking updateBooking(Long bookingId, LocalDate newCheckInDate, LocalDate newCheckOutDate) throws EntityNotFoundException;
    Booking findById(Long id);
    List<Booking> getUserBookings(Long userId);
    List<Booking> findAllBookings();

    void updateRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate, boolean isAvailable);
    void cancelBooking(Long bookingId);
    void deleteBookingById(long bookingId) throws EntityNotFoundException;

    boolean canReview(Long userId, Long roomId);
    boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate);
    boolean isCancellationAllowed(Long bookingId);

    int getTotalNumberOfBookings();

    List<RoomAvailabilityDto> getBookedDates();

}
