package com.paololauria.bnb.model.repository.abstractions;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.RoomAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface RoomAvailabilityRepository extends JpaRepository<RoomAvailability, Long> {
    List<RoomAvailability> findByRoomAndAvailabilityDate(Room room, LocalDate availabilityDate);
}
