package com.paololauria.bnb.services.implementations;

import com.paololauria.bnb.model.entities.Review;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.model.repository.abstractions.BookingRepository;
import com.paololauria.bnb.model.repository.abstractions.ReviewRepository;
import com.paololauria.bnb.model.repository.abstractions.RoomRepository;
import com.paololauria.bnb.services.abstraction.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JPARoomService implements RoomService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final ReviewRepository reviewRepository;

    public JPARoomService(BookingRepository bookingRepository, RoomRepository roomRepository, ReviewRepository reviewRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.reviewRepository = reviewRepository;
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
    public List<Review> findAllReviewByRoomId(Long roomId) {
        return reviewRepository.findByRoomRoomId(roomId);
    }
    @Override
    public List<Review> findReviewByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    @Override
    public void createReview(Review feedback, User user) {
        reviewRepository.save(feedback);
    }
}
