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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String toCoin; // Numérateur (Monet Acheté) “TO”
    private double toCoinValue; // JVM du Numérateur
    private double toCoinUnitValue; // Calculer la valeur unitaire

    private String fromCoin; // Dénominateur (Monet échanger pour monet acheté) “FROM”
    private double fromCoinValue; // JVM du Dénominateur
    private double fromCoinUnitValue; // Calculer la valeur unitaire

    private LocalDateTime transactionDate; // Date de transaction

    private String wallet; // Wallet
    private String exchange; // Exchange
}
