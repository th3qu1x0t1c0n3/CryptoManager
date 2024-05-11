package quixotic.projects.cryptomanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDateTime transactionDate;

    private String wallet;
    private String exchange;

    @Builder
    public Transaction(Long id, String toCoin, double toCoinQuantity, double toCoinValue, String fromCoin, double fromCoinQuantity, double fromCoinValue, LocalDateTime transactionDate, String wallet, String exchange) {
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
    }

    public double calculateUnitValue(double value, double quantity) {
        return value / quantity;
    }
}
