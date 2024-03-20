package com.paololauria.bnb.api.restcontrollers;

import com.paololauria.bnb.dtos.RoomDetailsDto;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.services.abstraction.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin
public class RoomController {
    RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RoomDetailsDto>> getAllRooms() {
        List<Room> films = roomService.findAll();
        List<RoomDetailsDto> result = films.stream().map(RoomDetailsDto::new).toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomDetailsDto> getRoomDetailsById(@PathVariable long roomId) {
        Room room = roomService.findById(roomId);
        if (room != null) {
            RoomDetailsDto result = new RoomDetailsDto(room);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
