package com.paololauria.bnb.api.restcontrollers;

import com.paololauria.bnb.dtos.UserDto;
import com.paololauria.bnb.model.entities.User;
import com.paololauria.bnb.services.abstraction.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
}
