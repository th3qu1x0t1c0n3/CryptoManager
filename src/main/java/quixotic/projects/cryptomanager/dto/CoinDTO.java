package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoinDTO {
    private String name;
    private double holdings;
    private double moneyInvested;
    private double avgPrice;
    private double totalValue;
    private double currentPrice = 1;

}
