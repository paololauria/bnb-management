package com.paololauria.bnb.api.restcontrollers;
import com.paololauria.bnb.dtos.AuthenticationRequestDto;
import com.paololauria.bnb.dtos.AuthenticationResponseDto;
import com.paololauria.bnb.dtos.ErrorResponseDto;
import com.paololauria.bnb.dtos.RegisterRequestDto;
import com.paololauria.bnb.model.exceptions.AuthErrorCode;
import com.paololauria.bnb.model.exceptions.DuplicateEmailException;
import com.paololauria.bnb.services.implementations.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register
            (@RequestBody RegisterRequestDto regRequestDto){
        try {
            return ResponseEntity.ok(authenticationService.register(regRequestDto));
        } catch (DuplicateEmailException de) {
            return ResponseEntity.badRequest().body(new ErrorResponseDto(AuthErrorCode.EMAIL_EXIST.toString(), AuthErrorCode.EMAIL_EXIST.getCode()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login
            (@RequestBody AuthenticationRequestDto authRequestDto){
        return ResponseEntity.ok(authenticationService.login(authRequestDto));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
