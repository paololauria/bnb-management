package com.paololauria.bnb.services.implementations;

import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.RoomAvailability;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.model.exceptions.UnauthorizedOperationException;
import com.paololauria.bnb.model.repository.abstractions.BookingRepository;
import com.paololauria.bnb.model.repository.abstractions.RoomAvailabilityRepository;
import com.paololauria.bnb.model.repository.abstractions.RoomRepository;
import com.paololauria.bnb.model.repository.abstractions.UserRepository;
import com.paololauria.bnb.services.implementations.JPABookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class JPABookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomAvailabilityRepository roomAvailabilityRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private JPABookingService bookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testMakeBooking() {
        // Mocking room and user
        Room room = new Room();
        room.setRoomId(1L);
        room.setPricePerNight(BigDecimal.valueOf(100));

        User user = new User();
        user.setId(1L);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Mocking repository save method
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking savedBooking = invocation.getArgument(0);
            savedBooking.setBookingId(1L); // Simulate ID assignment by database
            return savedBooking;
        });

        // Test
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(1);
        Booking booking = bookingService.makeBooking(1L, 1L, checkInDate, checkOutDate);

        assertNotNull(booking);
        assertEquals(1L, booking.getBookingId());
        assertEquals(room, booking.getRoom());
        assertEquals(user, booking.getUser());
        assertEquals(checkInDate, booking.getCheckInDate());
        assertEquals(checkOutDate, booking.getCheckOutDate());
        assertEquals(BigDecimal.valueOf(200), booking.getTotalPrice());

        // Verify that repository methods were called
        verify(roomRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testMakeBookingUnavailableRoom() {
        // Mocking room
        Room room = new Room();
        room.setRoomId(1L);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        // Mocking overlapping bookings
        List<Booking> overlappingBookings = new ArrayList<>();
        overlappingBookings.add(new Booking()); // Add a fake booking to simulate room is not available
        when(bookingRepository.findOverlappingBookings(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(overlappingBookings);

        // Test
        LocalDate checkInDate = LocalDate.now();
        LocalDate checkOutDate = LocalDate.now().plusDays(1);
        assertThrows(UnauthorizedOperationException.class,
                () -> bookingService.makeBooking(1L, 1L, checkInDate, checkOutDate));

        // Verify that repository methods were called
        verify(roomRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).findOverlappingBookings(anyLong(), any(LocalDate.class), any(LocalDate.class));
        verifyNoMoreInteractions(bookingRepository); // No further interactions should occur
    }
}
