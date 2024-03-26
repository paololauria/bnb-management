package com.paololauria.bnb.api.restcontrollers;

import com.paololauria.bnb.dtos.ReviewDto;
import com.paololauria.bnb.dtos.UserDto;
import com.paololauria.bnb.model.entities.Review;
import com.paololauria.bnb.model.entities.Room;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.services.abstraction.RoomService;
import com.paololauria.bnb.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    UserService userService;
    RoomService roomService;

    @Autowired
    public UserController(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserDetailsById(@PathVariable long id, @AuthenticationPrincipal User user) {
        Optional<User> os = userService.findById(id);
        if (os.isPresent()) {
            UserDto userDto = new UserDto(os.get());
            return ResponseEntity.ok(userDto);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin:update') or hasAuthority('manager:update')")
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<User> users = userService.findAllUser();
        List<UserDto> result = users.stream().map(UserDto::new).toList();

        return ResponseEntity.ok(result);

    }

    @PostMapping("/{userId}/profile-image")
    public ResponseEntity<String> updateUserProfileImage(@PathVariable Long userId,
                                                         @RequestBody String imageUrl,
                                                         @AuthenticationPrincipal User userDto) {
        Long loggedInUserId = userDto.getId();
        if (!userId.equals(loggedInUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Non sei autorizzato ad aggiornare l'immagine del profilo di questo utente");
        }

        try {
            String updatedImageUrl = userService.updateUserProfileImage(userId, imageUrl);
            return ResponseEntity.ok(updatedImageUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'aggiornamento dell'immagine del profilo: " + e.getMessage());
        }
    }


    @GetMapping("/{userId}/review")
    public ResponseEntity<List<ReviewDto>> getReviewsByUserId(@PathVariable Long userId) {
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User user = userOpt.get();
        List<Review> reviews = roomService.findReviewByUser(user.getId());
        if (reviews != null) {
            List<ReviewDto> reviewDtos = new ArrayList<>();

            for (Review review : reviews) {
                ReviewDto reviewDto = new ReviewDto(review);
                reviewDtos.add(reviewDto);
            }

            return ResponseEntity.ok(reviewDtos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{roomId}/review")
    public ResponseEntity<ReviewDto> createReview(@RequestBody ReviewDto reviewDto, @AuthenticationPrincipal User user, @PathVariable long roomId) throws URISyntaxException {
        Room room = roomService.findById(roomId);
        Optional<User> u = userService.findById(user.getId());
        if(u.isPresent()){
            Review rw = reviewDto.fromDto(room, u.get());
            roomService.createReview(rw, user);
            URI location = new URI("/api/user/review/" + rw.getId());
            ReviewDto created = new ReviewDto(rw);
            return ResponseEntity.created(location).body(created);}
        else {
            return ResponseEntity.badRequest().build();}
    }


}
