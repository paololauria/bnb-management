package com.paololauria.bnb.api.restcontrollers;
import com.paololauria.bnb.dtos.BookingRequestDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.services.abstraction.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/make")
    public ResponseEntity<Booking> makeBooking(@RequestBody BookingRequestDto bookingRequest) {
        Booking booking = bookingService.makeBooking(
                bookingRequest.getRoomId(),
                bookingRequest.getGuestName(),
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

    @PostMapping("/check-availability")
    public ResponseEntity<Boolean> checkAvailability(@RequestBody BookingRequestDto bookingRequest) {
        boolean isAvailable = bookingService.checkAvailability(
                bookingRequest.getRoomId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate()
        );
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }
}
