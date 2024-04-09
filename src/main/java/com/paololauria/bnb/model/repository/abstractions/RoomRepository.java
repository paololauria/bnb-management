package com.paololauria.bnb.model.repository.abstractions;
import com.paololauria.bnb.model.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
