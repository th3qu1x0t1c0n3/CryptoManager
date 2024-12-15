package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.enums.Network;

import java.time.LocalDateTime;

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

    private String notes;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;
}
