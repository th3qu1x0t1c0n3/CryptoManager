package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private Long id;

    private String toCoin;
    private double toCoinQuantity;
    private double toCoinValue;
    private double toCoinUnitValue;

    private String fromCoin;
    private double fromCoinQuantity;
    private double fromCoinValue;
    private double fromCoinUnitValue;

    private LocalDateTime transactionDate;

    private String wallet;
    private String exchange;

}
