package quixotic.projects.cryptomanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KellyCriterion {
    private double profits;
    private double losses;
    private double profitLoss;
    private int totalWin;
    private int totalLoss;
    private double winRate;
    private double lossRate;
    private double riskRewardRatio;
    private double kellyCriterion;
}
