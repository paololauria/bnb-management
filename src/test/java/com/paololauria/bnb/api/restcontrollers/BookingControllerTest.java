package com.paololauria.bnb.api.restcontrollers;

import com.paololauria.bnb.dtos.BookingRequestDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.services.abstraction.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    private BookingRequestDto bookingRequest;

    @BeforeEach
    void setUp() {
        // Inizializza i dati di input per il test
        bookingRequest = new BookingRequestDto();
        bookingRequest.setRoomId(1L);
        bookingRequest.setUserId(1L);
        bookingRequest.setCheckInDate(LocalDate.now());
        bookingRequest.setCheckOutDate(LocalDate.now().plusDays(3));
    }

    @Test
    void makeBooking_ReturnsCreatedBooking() {
        // Mocking il comportamento del servizio di prenotazione
        Booking expectedBooking = new Booking(); // Supponiamo che ci sia un costruttore o un builder per Booking
        when(bookingService.makeBooking(anyLong(), anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(expectedBooking);

        // Chiamata al metodo del controller da testare
        ResponseEntity<Booking> responseEntity = bookingController.makeBooking(bookingRequest);

        // Verifica delle interazioni e delle asserzioni
        verify(bookingService, times(1)).makeBooking(anyLong(), anyLong(), any(LocalDate.class), any(LocalDate.class));
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(expectedBooking, responseEntity.getBody());
    }

    @Test
    void makeBooking_UpdatesRoomAvailability() {
        // Mocking il comportamento del servizio di prenotazione
        Booking booking = new Booking(); // Supponiamo che ci sia un costruttore o un builder per Booking
        when(bookingService.makeBooking(anyLong(), anyLong(), any(LocalDate.class), any(LocalDate.class))).thenReturn(booking);

        // Chiamata al metodo del controller da testare
        ResponseEntity<Booking> responseEntity = bookingController.makeBooking(bookingRequest);

        // Verifica delle interazioni e delle asserzioni
        verify(bookingService, times(1)).updateRoomAvailability(eq(booking.getRoom()), eq(booking.getCheckInDate()), eq(booking.getCheckOutDate()), eq(false));
    }




}
