package com.paololauria.bnb.services.implementations;

import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Review;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.model.exceptions.UnauthorizedOperationException;
import com.paololauria.bnb.model.repository.abstractions.BookingRepository;
import com.paololauria.bnb.model.repository.abstractions.ReviewRepository;
import com.paololauria.bnb.model.repository.abstractions.RoomRepository;
import com.paololauria.bnb.services.abstraction.BookingService;
import com.paololauria.bnb.services.abstraction.RoomService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JPARoomService implements RoomService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;
    private final BookingService bookingService;

    public JPARoomService(BookingRepository bookingRepository, RoomRepository roomRepository, ReviewRepository reviewRepository, BookingService bookingService) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.reviewRepository = reviewRepository;
        this.bookingService = bookingService;
    }

    @Override
    public double getAverageOccupancyRate() {
        List<Room> rooms = roomRepository.findAll();
        int totalRooms = rooms.size();
        int totalOccupiedRooms = (int) rooms
                .stream()
                .filter(room -> !bookingService.isRoomAvailable
                        (room, LocalDate.now(), LocalDate.now().plusDays(1)))
                .count();
        return totalRooms > 0 ? ((double) totalOccupiedRooms / totalRooms) * 100 : 0;
    }

    @Override
    public Room findById(Long id) {
        Optional<Room> roomOptional = roomRepository.findById(id);
        return roomOptional.orElse(null);
    }

    @Override
    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, int maxGuests) {
        List<Room> allRooms = roomRepository.findAll();
        List<Room> availableRooms = new ArrayList<>();

        for (Room room : allRooms) {
            if (bookingService.isRoomAvailable(room, checkInDate, checkOutDate) && room.getMaxGuest() >= maxGuests) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    @Override
    public List<Review> findAllReviewByRoomId(Long roomId) {
        return reviewRepository.findByRoomRoomId(roomId);
    }
    @Override
    public List<Review> findReviewByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    @Override
    public void createReview(Review review, User user) {
        List<Booking> userBookings = bookingService.getUserBookings(user.getId());

        // Verifica se l'utente ha già recensito la prenotazione
        boolean hasReviewed = userBookings.stream()
                .anyMatch(booking -> reviewRepository.existsByBookingAndUser(booking, user));

        if (hasReviewed) {
            throw new UnauthorizedOperationException("L'utente può lasciare una sola recensione per ciascun soggiorno");
        }

        // Salva la recensione se l'utente non ha già recensito la prenotazione
        reviewRepository.save(review);
    }
}
