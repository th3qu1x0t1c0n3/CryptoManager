package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.security.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User toUser() {
        validateNewUser(this);
        return User.builder()
                .username(this.username)
                .password(this.password)
                .role(Role.USER)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();
    }

    private void validateNewUser(SignUpDTO signUpDTO) {
        if (signUpDTO.getUsername() == null || signUpDTO.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (signUpDTO.getPassword() == null || signUpDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (signUpDTO.getFirstName() == null || signUpDTO.getFirstName().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (signUpDTO.getLastName() == null || signUpDTO.getLastName().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }
}
