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
public class UserDTO {
    private Long id;
    private String email;
    private Role role;
    private String firstName;
    private String lastName;
    private String token;

    public UserDTO(User user, String token){
        this.id = user.getId();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.token = token;
    }

}
