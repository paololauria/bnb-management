package com.paololauria.bnb.services.abstraction;

import com.paololauria.bnb.model.entities.Review;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room findById(Long id);
    List<Room> findAll();
    List<Review> findAllReviewByRoomId(Long roomId);
    List<Review> findReviewByUser(Long userId);
    void createReview(Review feedback, User user);
}
