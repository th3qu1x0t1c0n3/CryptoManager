package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.Transaction;
import quixotic.projects.cryptomanager.model.User;

import java.time.LocalDate;
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

    private LocalDate transactionDate;

    private String wallet;
    private String exchange;

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.toCoin = transaction.getToCoin();
        this.toCoinQuantity = transaction.getToCoinQuantity();
        this.toCoinValue = transaction.getToCoinValue();
        this.toCoinUnitValue = transaction.getToCoinUnitValue();

        this.fromCoin = transaction.getFromCoin();
        this.fromCoinQuantity = transaction.getFromCoinQuantity();
        this.fromCoinValue = transaction.getFromCoinValue();
        this.fromCoinUnitValue = transaction.getFromCoinUnitValue();

        this.transactionDate = transaction.getTransactionDate();
        this.wallet = transaction.getWallet();
        this.exchange = transaction.getExchange();
    }

    public Transaction toTransaction(User user) {
        return Transaction.builder()
                .toCoin(this.toCoin)
                .toCoinQuantity(this.toCoinQuantity)
                .toCoinValue(this.toCoinValue)
                .fromCoin(this.fromCoin)
                .fromCoinQuantity(this.fromCoinQuantity)
                .fromCoinValue(this.fromCoinValue)
                .transactionDate(this.transactionDate)
                .wallet(this.wallet)
                .exchange(this.exchange)
                .user(user)
                .build();
    }
}
