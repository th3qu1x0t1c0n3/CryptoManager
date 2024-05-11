package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.CoinTransaction;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoinTransactionDTO {
    private String name;
    private double quantity;
    private double value;
    private double unitValue;

}
