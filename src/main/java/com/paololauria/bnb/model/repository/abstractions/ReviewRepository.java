package com.paololauria.bnb.model.repository.abstractions;

import com.paololauria.bnb.model.entities.Booking;
import com.paololauria.bnb.model.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByUserId(Long userId);
    List<Review> findByRoomRoomId(Long roomId);
}
