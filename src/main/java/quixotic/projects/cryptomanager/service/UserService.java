package quixotic.projects.cryptomanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import quixotic.projects.cryptomanager.dto.SignInDTO;
import quixotic.projects.cryptomanager.dto.SignUpDTO;
import quixotic.projects.cryptomanager.dto.UserDTO;
import quixotic.projects.cryptomanager.exception.badRequestException.UsernameTakenException;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.repository.UserRepository;
import quixotic.projects.cryptomanager.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserDTO authenticateCook(SignInDTO signInDTO) {
        return new UserDTO(
                userRepository.findByEmail(signInDTO.getEmail()).orElseThrow(),
                generateToken(signInDTO.getEmail(), signInDTO.getPassword())
        );
    }

    public UserDTO createCook(SignUpDTO signUpDTO) {
        User cook = signUpDTO.toUser();
        cook.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        try {
            cook = userRepository.save(cook);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Error: " + e);
            throw new UsernameTakenException();
        }

        String token = generateToken(cook.getUsername(), signUpDTO.getPassword());
        return new UserDTO(cook, token);
    }

    private String generateToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenProvider.generateToken(authentication);
    }

    public UserDTO getMe(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        return new UserDTO(userRepository.findByEmail(username).orElseThrow(), token);
    }
}
