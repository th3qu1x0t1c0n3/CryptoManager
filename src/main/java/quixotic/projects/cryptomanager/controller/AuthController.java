package quixotic.projects.cryptomanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cryptomanager.dto.SignInDTO;
import quixotic.projects.cryptomanager.dto.SignUpDTO;
import quixotic.projects.cryptomanager.dto.UserDTO;
import quixotic.projects.cryptomanager.service.UserService;

@RestController
@RequestMapping("/api/v1/port/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody SignInDTO signInDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(userService.authenticateUser(signInDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signupUser(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(userService.createUser(signUpDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(HttpServletRequest request){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body(
                userService.getMe(request.getHeader("Authorization")));
    }

}
