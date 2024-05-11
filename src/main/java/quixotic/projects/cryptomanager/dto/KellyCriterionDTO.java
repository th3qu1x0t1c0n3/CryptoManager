package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.KellyCriterion;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class KellyCriterionDTO {
    private double profits;
    private double losses;
    private double profitLoss;
    private int totalWin;
    private int totalLoss;
    private double winRate;
    private double lossRate;
    private double riskRewardRatio;
    private double kellyCriterion;

    public KellyCriterionDTO(KellyCriterion kellyCriterion) {
        this.profits = kellyCriterion.getProfits();
        this.losses = kellyCriterion.getLosses();
        this.profitLoss = kellyCriterion.getProfitLoss();
        this.totalWin = kellyCriterion.getTotalWin();
        this.totalLoss = kellyCriterion.getTotalLoss();
        this.winRate = kellyCriterion.getWinRate();
        this.lossRate = kellyCriterion.getLossRate();
        this.riskRewardRatio = kellyCriterion.getRiskRewardRatio();
        this.kellyCriterion = kellyCriterion.getKellyCriterion();
    }
}
