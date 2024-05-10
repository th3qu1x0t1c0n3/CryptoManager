//package quixotic.projects.cryptomanager.outsource;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import quixotic.projects.cryptomanager.repository.UserRepository;
//import quixotic.projects.cryptomanager.security.JwtTokenProvider;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//    private final UserRepository cookRepository;
//    private final AuthenticationManager authenticationManager;
//    private final JwtTokenProvider jwtTokenProvider;
//    private final PasswordEncoder passwordEncoder;
//
//    public CookDTO authenticateCook(SignInDTO signInDTO) {
//        return new CookDTO(
//                cookRepository.findCookByUsername(signInDTO.getUsername()).orElseThrow(),
//                generateToken(signInDTO.getUsername(), signInDTO.getPassword())
//        );
//    }
//    public CookDTO createCook(SignUpDTO signUpDTO) {
//        Cook cook = signUpDTO.toCook();
//        cook.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
//
//        try {
//            cook = cookRepository.save(cook);
//        } catch (DataIntegrityViolationException e) {
//            System.out.println("Error: " + e);
//            throw new UsernameTakenException();
//        }
//
//        String token = generateToken(cook.getUsername(), signUpDTO.getPassword());
//        return new CookDTO(cook, token);
//    }
//    private String generateToken(String username, String password) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//        return jwtTokenProvider.generateToken(authentication);
//    }
//
//    public CookDTO getMe(String token) {
//        String username = jwtTokenProvider.getUsernameFromJWT(token);
//        return new CookDTO(cookRepository.findCookByUsername(username).orElseThrow());
//    }
//}
