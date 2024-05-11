package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.Transaction;
import quixotic.projects.cryptomanager.model.User;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private Long id;

    private CoinTransactionDTO toCoin;
    private CoinTransactionDTO fromCoin;

    private LocalDate transactionDate;

    private String wallet;
    private String exchange;

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();

        this.toCoin = CoinTransactionDTO.builder()
                .name(transaction.getToCoinName())
                .quantity(transaction.getToCoinQuantity())
                .value(transaction.getToCoinValue())
                .unitValue(transaction.getToCoinUnitValue())
                .build();
        this.fromCoin = CoinTransactionDTO.builder()
                .name(transaction.getFromCoinName())
                .quantity(transaction.getFromCoinQuantity())
                .value(transaction.getFromCoinValue())
                .unitValue(transaction.getFromCoinUnitValue())
                .build();

        this.transactionDate = transaction.getTransactionDate();
        this.wallet = transaction.getWallet();
        this.exchange = transaction.getExchange();
    }

    public Transaction toTransaction(User user) {
        return Transaction.builder()
                .toCoin(this.toCoin.getName())
                .toCoinQuantity(this.toCoin.getQuantity())
                .toCoinValue(this.toCoin.getValue())
                .fromCoin(this.fromCoin.getName())
                .fromCoinQuantity(this.fromCoin.getQuantity())
                .fromCoinValue(this.fromCoin.getValue())
                .transactionDate(this.transactionDate)
                .wallet(this.wallet)
                .exchange(this.exchange)
                .user(user)
                .build();
    }
}
