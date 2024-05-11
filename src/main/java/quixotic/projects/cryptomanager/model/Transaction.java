package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.dto.TransactionDTO;

import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
//@AllArgsConstructor
//@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CoinTransaction toCoin;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private CoinTransaction fromCoin;

    private LocalDate transactionDate;

    private String wallet;
    private String exchange;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Builder
    public Transaction(Long id, String toCoin, double toCoinQuantity, double toCoinValue, String fromCoin, double fromCoinQuantity, double fromCoinValue, LocalDate transactionDate, String wallet, String exchange, User user) {
        this.id = id;

        this.toCoin = CoinTransaction.builder()
                .name(toCoin)
                .quantity(toCoinQuantity)
                .value(toCoinValue)
                .unitValue(calculateUnitValue(toCoinValue, toCoinQuantity))
                .build();
        this.fromCoin = CoinTransaction.builder()
                .name(fromCoin)
                .quantity(fromCoinQuantity)
                .value(fromCoinValue)
                .unitValue(calculateUnitValue(fromCoinValue, fromCoinQuantity))
                .build();

        this.transactionDate = transactionDate;

        if (Objects.equals(wallet, "") && Objects.equals(exchange, "")) {
            throw new IllegalArgumentException("Wallet and Exchange cannot be empty");
        }
        this.wallet = wallet;
        this.exchange = exchange;

        this.user = user;
    }

    public double calculateUnitValue(double value, double quantity) {
        return value / quantity;
    }

    public void updateTransaction(Transaction transaction) {
        this.toCoin = CoinTransaction.builder()
                .name(transaction.getToCoinName())
                .quantity(transaction.getToCoinQuantity())
                .value(transaction.getToCoinValue())
                .unitValue(calculateUnitValue(transaction.getToCoinValue(), transaction.getToCoinQuantity()))
                .build();
        this.fromCoin = CoinTransaction.builder()
                .name(transaction.getFromCoinName())
                .quantity(transaction.getFromCoinQuantity())
                .value(transaction.getFromCoinValue())
                .unitValue(calculateUnitValue(transaction.getFromCoinValue(), transaction.getFromCoinQuantity()))
                .build();

        this.transactionDate = transaction.getTransactionDate();
        this.wallet = transaction.getWallet();
        this.exchange = transaction.getExchange();
    }

    public double getToCoinValue() {
        return toCoin.getValue();
    }

    public double getToCoinQuantity() {
        return toCoin.getQuantity();
    }

    public String getToCoinName() {
        return toCoin.getName();
    }

    public double getToCoinUnitValue() {
        return toCoin.getUnitValue();
    }

    public double getFromCoinValue() {
        return fromCoin.getValue();
    }

    public double getFromCoinQuantity() {
        return fromCoin.getQuantity();
    }

    public String getFromCoinName() {
        return fromCoin.getName();
    }

    public double getFromCoinUnitValue() {
        return fromCoin.getUnitValue();
    }


}
