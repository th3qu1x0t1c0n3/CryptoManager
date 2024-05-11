package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.dto.TransactionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String toCoin; // Numérateur (Monet Acheté) “TO”
    private double toCoinQuantity; // Quantité de Monet Acheté
    private double toCoinValue; // JVM du Numérateur
    private double toCoinUnitValue; // Calculer la valeur unitaire

    private String fromCoin; // Dénominateur (Monet échanger pour monet acheté) “FROM”
    private double fromCoinQuantity; // Quantité de Monet échanger
    private double fromCoinValue; // JVM du Dénominateur
    private double fromCoinUnitValue; // Calculer la valeur unitaire

    private LocalDate transactionDate;

    private String wallet;
    private String exchange;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

    @Builder
    public Transaction(Long id, String toCoin, double toCoinQuantity, double toCoinValue, String fromCoin, double fromCoinQuantity, double fromCoinValue, LocalDate transactionDate, String wallet, String exchange, User user) {
        this.id = id;
        this.toCoin = toCoin;
        this.toCoinQuantity = toCoinQuantity;
        this.toCoinValue = toCoinValue;
        this.toCoinUnitValue = calculateUnitValue(toCoinValue, toCoinQuantity);

        this.fromCoin = fromCoin;
        this.fromCoinQuantity = fromCoinQuantity;
        this.fromCoinValue = fromCoinValue;
        this.fromCoinUnitValue = calculateUnitValue(fromCoinValue, fromCoinQuantity);

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

    public void updateTransaction(TransactionDTO transactionDTO) {
        this.toCoin = transactionDTO.getToCoin();
        this.toCoinQuantity = transactionDTO.getToCoinQuantity();
        this.toCoinValue = transactionDTO.getToCoinValue();
        this.toCoinUnitValue = calculateUnitValue(transactionDTO.getToCoinValue(), transactionDTO.getToCoinQuantity());

        this.fromCoin = transactionDTO.getFromCoin();
        this.fromCoinQuantity = transactionDTO.getFromCoinQuantity();
        this.fromCoinValue = transactionDTO.getFromCoinValue();
        this.fromCoinUnitValue = calculateUnitValue(transactionDTO.getFromCoinValue(), transactionDTO.getFromCoinQuantity());

        this.transactionDate = transactionDTO.getTransactionDate();
        this.wallet = transactionDTO.getWallet();
        this.exchange = transactionDTO.getExchange();
    }
}
