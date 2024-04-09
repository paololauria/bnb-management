package com.paololauria.bnb.services.implementations;
import com.paololauria.bnb.dtos.RoomAvailabilityDto;
import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.RoomAvailability;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.model.exceptions.EntityNotFoundException;
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
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Stanza non trovata"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // Verifica se la stanza è disponibile per l'intero periodo di soggiorno specificato
        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            throw new UnauthorizedOperationException("La stanza non è disponibile per le date richieste");
        }

        // Verifica se la stanza è disponibile a partire dalla data di check-in della nuova prenotazione
        if (!isRoomAvailable(room, checkInDate, checkInDate.plusDays(1))) {
            throw new UnauthorizedOperationException("La stanza non è disponibile per la data di check-in");
        }

        // Verifica se la stanza è disponibile fino alla data di check-out della nuova prenotazione
        if (!isRoomAvailable(room, checkOutDate.minusDays(1), checkOutDate)) {
            throw new UnauthorizedOperationException("La stanza non è disponibile per la data di check-out");
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
    public Booking updateBooking(Long bookingId, LocalDate newCheckInDate, LocalDate newCheckOutDate) throws EntityNotFoundException {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("La prenotazione con " + bookingId + " ID non trovata", Booking.class));

        // Controlla se le nuove date di check-in e check-out si sovrappongono con altre prenotazioni esistenti per la stessa stanza
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookings(booking.getRoom().getRoomId(), newCheckInDate, newCheckOutDate);
        overlappingBookings.remove(booking); // Rimuovi la prenotazione attuale dalla lista delle sovrapposizioni
        if (!overlappingBookings.isEmpty()) {
            throw new UnauthorizedOperationException("Le nuove date di prenotazione si sovrappongono alle prenotazioni esistenti per la stessa camera");
        }

        // Controlla se la stanza è disponibile per il nuovo periodo di soggiorno specificato
        if (!isRoomAvailable(booking.getRoom(), newCheckInDate, newCheckOutDate)) {
            throw new UnauthorizedOperationException("La stanza non è disponibile per le date richieste");
        }

        // Aggiorna le date di check-in e check-out
        booking.setCheckInDate(newCheckInDate);
        booking.setCheckOutDate(newCheckOutDate);

        // Calcola il nuovo prezzo totale
        booking.setTotalPrice(calculateTotalPrice(booking.getRoom().getPricePerNight(), newCheckInDate, newCheckOutDate));

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

    @Override
    public List<Booking> findAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public void deleteBookingById(long bookingId) throws EntityNotFoundException {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            bookingRepository.deleteById(bookingId);
        } else {
            throw new EntityNotFoundException("La prenotazione con " + bookingId + " ID non trovata", Booking.class);
        }
    }

    @Override
    public int getTotalNumberOfBookings() {
        return bookingRepository.findAll().size();
    }




    public BigDecimal calculateTotalPrice(BigDecimal pricePerNight, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate == null || checkOutDate == null) {
            throw new IllegalArgumentException("Le date di check-in e check-out non possono essere null");
        }

        long numberOfNights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return pricePerNight.multiply(BigDecimal.valueOf(numberOfNights));
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

        if (!userBookings.isEmpty()) {

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
