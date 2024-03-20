package com.paololauria.bnb.services.implementations;

import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.repository.abstractions.BookingRepository;
import com.paololauria.bnb.model.repository.abstractions.RoomRepository;
import com.paololauria.bnb.services.abstraction.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JPARoomService implements RoomService {
    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;

    public JPARoomService(BookingRepository bookingRepository, RoomRepository roomRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
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
}
