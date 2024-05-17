package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class KellyCriterion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
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
}
