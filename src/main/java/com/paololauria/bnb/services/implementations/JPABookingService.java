package com.paololauria.bnb.services.implementations;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.RoomAvailability;
import com.paololauria.bnb.model.exceptions.UnauthorizedOperationException;
import com.paololauria.bnb.model.repository.abstractions.BookingRepository;
import com.paololauria.bnb.model.repository.abstractions.RoomAvailabilityRepository;
import com.paololauria.bnb.model.repository.abstractions.RoomRepository;
import com.paololauria.bnb.services.abstraction.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class JPABookingService implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RoomAvailabilityRepository roomAvailabilityRepository;

    public JPABookingService(BookingRepository bookingRepository, RoomRepository roomRepository, RoomAvailabilityRepository roomAvailabilityRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.roomAvailabilityRepository = roomAvailabilityRepository;
    }

    @Override
    public Booking makeBooking(Long roomId, String guestName,
                               LocalDate checkInDate, LocalDate checkOutDate) {

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            throw new UnauthorizedOperationException("Room is not available for the specified dates");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setGuestName(guestName);
        booking.setCheckInDate(checkInDate);
        booking.setCheckOutDate(checkOutDate);
        booking.setTotalPrice(calculateTotalPrice(room.getPricePerNight(), checkInDate, checkOutDate));

        return bookingRepository.save(booking);
    }

    @Override
    public void updateRoomAvailability(Room room, LocalDate checkInDate, LocalDate checkOutDate, boolean isAvailable) {
        for (LocalDate date = checkInDate; date.isBefore(checkOutDate.plusDays(1)); date = date.plusDays(1)) {
            List<RoomAvailability> availabilityList = roomAvailabilityRepository.findByRoomAndAvailabilityDate(room, date);
            if (availabilityList.isEmpty()) {
                RoomAvailability availability = new RoomAvailability();
                availability.setRoom(room);
                availability.setAvailabilityDate(date);
                availability.setAvailable(isAvailable);
                roomAvailabilityRepository.save(availability);
            } else {
                for (RoomAvailability availability : availabilityList) {
                    availability.setAvailable(isAvailable);
                    roomAvailabilityRepository.save(availability);
                }
            }
        }
    }

    @Override
    @Transactional
    public boolean checkAvailability(Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(roomId, checkInDate, checkOutDate);
        return overlappingBookings.isEmpty();
    }

    private BigDecimal calculateTotalPrice(BigDecimal pricePerNight, LocalDate checkInDate, LocalDate checkOutDate) {
        long numNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return pricePerNight.multiply(BigDecimal.valueOf(numNights));
    }

    private boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(room.getRoomId(), checkInDate, checkOutDate);
        return overlappingBookings.isEmpty();
    }

}
