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
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    public User toUser() {
        return User.builder()
                .email(this.email)
                .password(this.password)
                .role(Role.USER)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .build();
    }
}
