package quixotic.projects.cryptomanager.model.chain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.User;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Token {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Long id;

    @Column(precision = 40, scale = 20)
    public BigDecimal balance;
    @ManyToOne
    public User user;
    @ManyToOne
    public Currency currency;
}
