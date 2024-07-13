package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class KellyCriterion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private User user;

    private double nbProfit;
    private double nbLoss;
    private double totalReturn;
    private int totalWin;
    private int totalLoss;

    private double winRate;
    private double lossRate;

    private double riskRewardRatio;
    private double kellyCriterion;

    @Builder
    public KellyCriterion(Long id, User user, double nbProfit, double nbLoss, double totalReturn, int totalWin, int totalLoss, double winRate, double lossRate) {
        this.id = id;
        this.user = user;
        this.nbProfit = nbProfit;
        this.nbLoss = nbLoss;
        this.totalReturn = totalReturn;
        this.totalWin = totalWin;
        this.totalLoss = totalLoss;
        this.winRate = winRate;
        this.lossRate = lossRate;

        this.riskRewardRatio = winRate / lossRate;
        this.kellyCriterion = calculateKellyCriterion();
    }

    private double calculateKellyCriterion() {
        return (winRate * (1 + riskRewardRatio) - 1) / riskRewardRatio;
    }
}
