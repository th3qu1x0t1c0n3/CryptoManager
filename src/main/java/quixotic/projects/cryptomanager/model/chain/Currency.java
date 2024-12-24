package quixotic.projects.cryptomanager.model.chain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.enums.CurrencyType;
import quixotic.projects.cryptomanager.model.enums.Network;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Currency {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;
    private String contractAddress;
    private CurrencyType type;
    private Network network;
    private String tokenName;
    private String tokenSymbol;
    private String tokenDecimal;
}
