package quixotic.projects.cryptomanager.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import quixotic.projects.cryptomanager.exception.forbiddenRequestExceptions.InvalidJwtException;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = loadUserByUsername(authentication.getPrincipal().toString());
        validateAuthentication(authentication, user);
        return new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username).orElseThrow();
    }

    private void validateAuthentication(Authentication authentication, User user) {
        if (!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword()))
            throw new InvalidJwtException("Incorrect username or password");
    }
}
