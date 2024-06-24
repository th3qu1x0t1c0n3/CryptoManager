package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.old.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String address;
    @Enumerated(EnumType.STRING)
    private Network network;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
}
