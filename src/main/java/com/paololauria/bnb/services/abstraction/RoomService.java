package com.paololauria.bnb.services.abstraction;
import com.paololauria.bnb.model.entities.Review;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {
    Room findById(Long id);

    List<Room> findAll();
    List<Room> findAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, int maxGuests);

    List<Review> findAllReviewByRoomId(Long roomId);
    List<Review> findReviewByUser(Long userId);

    void createReview(Review feedback, User user);

    double getAverageOccupancyRate();

}
