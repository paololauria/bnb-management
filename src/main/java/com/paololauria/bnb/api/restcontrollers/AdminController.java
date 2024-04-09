package com.paololauria.bnb.api.restcontrollers;
import com.paololauria.bnb.dtos.BookingRequestDto;
import com.paololauria.bnb.dtos.UpdateBookingRequest;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.exceptions.EntityNotFoundException;
import com.paololauria.bnb.model.exceptions.UnauthorizedOperationException;
import com.paololauria.bnb.services.abstraction.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    BookingService bookingService;

    @Autowired
    public AdminController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @DeleteMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('admin:delete') or hasAuthority('manager:delete')")
    public ResponseEntity<?> deleteBookingById(@PathVariable long bookingId) {
        try {
            bookingService.deleteBookingById(bookingId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException ignored){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/createBooking")
    @PreAuthorize("hasAuthority('admin:create') or hasAuthority('manager:create')")
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto) {
        try {
            Booking booking = bookingRequestDto.fromDto();
            Booking createdBooking = bookingService.makeBooking(booking.getRoom().getRoomId(), booking.getUser().getId(), booking.getCheckInDate(), booking.getCheckOutDate());
            URI location = new URI("/api/bookings/" + createdBooking.getBookingId());
            return ResponseEntity.created(location).build();
        } catch (RuntimeException | URISyntaxException e) {
            return ResponseEntity.badRequest().body("Failed to create booking: " + e.getMessage());
        }
    }

    @PutMapping("/{bookingId}")
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('manager:update')")
    public ResponseEntity<?> updateBooking(@PathVariable long bookingId, @Valid @RequestBody UpdateBookingRequest updateBookingRequest) {
        try {
            LocalDate newCheckInDate = updateBookingRequest.getCheckInDate();
            LocalDate newCheckOutDate = updateBookingRequest.getCheckOutDate();
            Booking updatedBooking = bookingService.updateBooking(bookingId, newCheckInDate, newCheckOutDate);
            return ResponseEntity.ok(updatedBooking);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedOperationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
