package com.paololauria.bnb.services.implementations;

import com.paololauria.bnb.dtos.RoomAvailabilityDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.RoomAvailability;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.model.exceptions.UnauthorizedOperationException;
import com.paololauria.bnb.model.repository.abstractions.*;
import com.paololauria.bnb.services.abstraction.BookingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JPABookingService implements BookingService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final RoomAvailabilityRepository roomAvailabilityRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public JPABookingService(BookingRepository bookingRepository, RoomRepository roomRepository, RoomAvailabilityRepository roomAvailabilityRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.roomAvailabilityRepository = roomAvailabilityRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Booking makeBooking(Long roomId, Long userId, LocalDate checkInDate, LocalDate checkOutDate) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            throw new UnauthorizedOperationException("Room is not available for the specified dates");
        }

        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setUser(user);
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
    public List<RoomAvailabilityDto> getBookedDates() {
        List<Booking> bookedRooms = bookingRepository.findAll();

        return bookedRooms.stream()
                .map(booking -> new RoomAvailabilityDto(
                        booking.getRoom().getRoomId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public Booking findById(Long id) {
        Optional<Booking> bookingOptional = bookingRepository.findById(id);
        return bookingOptional.orElse(null);
    }

    private BigDecimal calculateTotalPrice(BigDecimal pricePerNight, LocalDate checkInDate, LocalDate checkOutDate) {
        long numNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return pricePerNight.multiply(BigDecimal.valueOf(numNights));
    }

    public boolean isRoomAvailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(room.getRoomId(), checkInDate, checkOutDate);
        return overlappingBookings.isEmpty();
    }

    @Override
    public boolean isCancellationAllowed(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking != null) {
            LocalDate currentDate = LocalDate.now();
            LocalDate checkInDate = booking.getCheckInDate();

            // Controlla se la data corrente è entro i 30 giorni dal check-in
            return currentDate.isBefore(checkInDate.minusDays(30));
        } else {
            return false;
        }
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);

        if (booking != null) {
            bookingRepository.delete(booking);
            // Aggiornare la disponibilità della stanza, ad esempio impostare nuovamente disponibile il periodo prenotato
            updateRoomAvailability(booking.getRoom(), booking.getCheckInDate(), booking.getCheckOutDate(), true);
        }
    }

    @Override
    public boolean canReview(Long userId, Long roomId) {
        // Trova tutte le prenotazioni dell'utente per la stanza specificata
        List<Booking> userBookings = bookingRepository.findByUserIdAndRoomRoomId(userId, roomId);

        // Verifica se l'utente ha prenotazioni per la stanza specificata
        if (!userBookings.isEmpty()) {
            // Scansiona tutte le prenotazioni dell'utente per la stanza
            for (Booking booking : userBookings) {
                // Ottieni la data di check-out di ciascuna prenotazione
                LocalDate checkOutDate = booking.getCheckOutDate();

                // Verifica se la data corrente è successiva alla data di check-out della prenotazione
                if (LocalDate.now().isAfter(checkOutDate)) {
                    // Controlla se l'utente ha già lasciato una recensione per questa prenotazione
                    boolean hasReviewed = reviewRepository.existsByBookingAndUser(booking, booking.getUser());

                    // Se l'utente non ha ancora lasciato una recensione per questa prenotazione,
                    // allora può lasciare una recensione
                    if (!hasReviewed) {
                        return true;
                    }
                }
            }
        }

        // Se l'utente non ha prenotazioni per la stanza specificata
        // o se ha già lasciato una recensione per tutte le prenotazioni valide,
        // restituisci false
        return false;
    }

}
