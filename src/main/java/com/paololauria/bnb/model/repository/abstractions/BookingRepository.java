package com.paololauria.bnb.model.repository.abstractions;
import com.paololauria.bnb.model.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT b FROM Booking b WHERE b.room.roomId = :roomId AND "
            + "((b.checkInDate BETWEEN :checkInDate AND :checkOutDate) OR "
            + "(b.checkOutDate BETWEEN :checkInDate AND :checkOutDate))")
    List<Booking> findOverlappingBookings(@Param("roomId") Long roomId,
                                          @Param("checkInDate") LocalDate checkInDate,
                                          @Param("checkOutDate") LocalDate checkOutDate);
}
