package quixotic.projects.cryptomanager.outsource;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import quixotic.projects.cryptomanager.dto.SignInDTO;
import quixotic.projects.cryptomanager.dto.SignUpDTO;
import quixotic.projects.cryptomanager.dto.UserDTO;

@Controller
@RequestMapping("/api/v1/port/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<UserDTO> authenticateCook(@RequestBody SignInDTO signInDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(userService.authenticateCook(signInDTO));
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signupCook(@RequestBody SignUpDTO signUpDTO){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(userService.createCook(signUpDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe(HttpServletRequest request){
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body(
                userService.getMe(request.getHeader("Authorization")));
    }

}
