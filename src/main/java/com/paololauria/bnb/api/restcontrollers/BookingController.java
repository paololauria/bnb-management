package com.paololauria.bnb.api.restcontrollers;
import com.paololauria.bnb.dtos.BookingRequestDto;
import com.paololauria.bnb.dtos.RoomAvailabilityDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.services.abstraction.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/make")
    public ResponseEntity<Booking> makeBooking(@RequestBody BookingRequestDto bookingRequest) {

        Long userId = bookingRequest.getUserId();

        Booking booking = bookingService.makeBooking(
                bookingRequest.getRoomId(),
                userId,
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate()
        );

        bookingService.updateRoomAvailability(
                booking.getRoom(),
                booking.getCheckInDate(),
                booking.getCheckOutDate(),
                false
        );

        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @GetMapping("/booked-dates")
    public ResponseEntity<List<RoomAvailabilityDto>> getBookedDates() {
        List<RoomAvailabilityDto> bookedDates = bookingService.getBookedDates();
        return new ResponseEntity<>(bookedDates, HttpStatus.OK);
    }


    @GetMapping("{userId}/confirm")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        List<Booking> userBookings = bookingService.getUserBookings(userId);

        if (userBookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(userBookings, HttpStatus.OK);
        }
    }

}
