package com.paololauria.bnb.services.abstraction;

import com.paololauria.bnb.model.entities.Room;

import java.util.List;
import java.util.Optional;

public interface RoomService {
    Room findById(Long id);
    List<Room> findAll();
}
