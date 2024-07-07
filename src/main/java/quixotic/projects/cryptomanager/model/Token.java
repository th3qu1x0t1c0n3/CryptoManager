package quixotic.projects.cryptomanager.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    public String tokenName;
    public String tokenSymbol;
    public String contractAddress;
    public String tokenDecimal;
    @ManyToOne
    public User user;
}
