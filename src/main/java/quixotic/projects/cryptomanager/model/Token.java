package quixotic.projects.cryptomanager.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    public BigDecimal balance;
    public String tokenName;
    public String tokenSymbol;
    public String contractAddress;
    public String tokenDecimal;
}
