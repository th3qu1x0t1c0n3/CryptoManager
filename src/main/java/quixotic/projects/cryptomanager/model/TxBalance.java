package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.chain.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TxBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String amount;
    @ManyToOne
    private Currency currency;
    @ManyToOne
    private Wallet wallet;
    private String costBasis;

}
