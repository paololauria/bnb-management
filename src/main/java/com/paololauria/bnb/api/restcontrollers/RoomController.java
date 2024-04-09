package com.paololauria.bnb.api.restcontrollers;
import com.paololauria.bnb.dtos.ReviewDto;
import com.paololauria.bnb.dtos.RoomDetailsDto;
import com.paololauria.bnb.model.entities.Review;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.services.abstraction.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

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

    @GetMapping("/{roomId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewByRoomId(@PathVariable long roomId) {
        List<Review> reviews = roomService.findAllReviewByRoomId(roomId);
        if (reviews.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<ReviewDto> reviewDtos = reviews.stream().map(ReviewDto::new).toList();
        return ResponseEntity.ok(reviewDtos);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Room>> searchAvailableRooms(
            @RequestParam("checkInDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam("checkOutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam("maxGuests") int maxGuests) {

        List<Room> availableRooms = roomService.findAvailableRooms(checkInDate, checkOutDate, maxGuests);
        return ResponseEntity.ok(availableRooms);
    }

}
