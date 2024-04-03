package com.paololauria.bnb.api.restcontrollers;
import com.paololauria.bnb.dtos.BookingRequestDto;
import com.paololauria.bnb.dtos.RoomAvailabilityDto;
import com.paololauria.bnb.dtos.RoomDetailsDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.services.abstraction.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/make")
    public ResponseEntity<BookingRequestDto> makeBooking(@RequestBody BookingRequestDto bookingRequest) {

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
        BookingRequestDto bookingRequestDto = new BookingRequestDto(booking);
        return new ResponseEntity<>(bookingRequestDto, HttpStatus.CREATED);
    }


    @GetMapping("/booked-dates")
    public ResponseEntity<List<RoomAvailabilityDto>> getBookedDates() {
        List<RoomAvailabilityDto> bookedDates = bookingService.getBookedDates();
        return new ResponseEntity<>(bookedDates, HttpStatus.OK);
    }


    @GetMapping("{userId}/confirm")
    public ResponseEntity<List<BookingRequestDto>> getUserBookings(@PathVariable Long userId) {
        List<Booking> userBookings = bookingService.getUserBookings(userId);
        List<BookingRequestDto> requestDtos = userBookings.stream().map(BookingRequestDto::new).toList();

        if (userBookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(requestDtos, HttpStatus.OK);
        }
    }

    @PostMapping("/cancel/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        boolean cancellationAllowed = bookingService.isCancellationAllowed(bookingId);

        if (cancellationAllowed) {
            bookingService.cancelBooking(bookingId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingRequestDto> getBookingDetailsById(@PathVariable long bookingId) {
        Booking booking = bookingService.findById(bookingId);
        if (booking != null) {
            BookingRequestDto result = new BookingRequestDto(booking);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
