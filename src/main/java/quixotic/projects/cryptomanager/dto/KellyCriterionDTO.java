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
    private double nbProfit;
    private double nbLoss;
    private double totalReturn;
    private int totalWin;
    private int totalLoss;
    private double winRate;
    private double lossRate;
    private double riskRewardRatio;
    private double kellyCriterion;

    public KellyCriterionDTO(KellyCriterion kellyCriterion) {
        this.nbProfit = kellyCriterion.getNbProfit();
        this.nbLoss = kellyCriterion.getNbLoss();
        this.totalReturn = kellyCriterion.getTotalReturn();
        this.totalWin = kellyCriterion.getTotalWin();
        this.totalLoss = kellyCriterion.getTotalLoss();
        this.winRate = kellyCriterion.getWinRate();
        this.lossRate = kellyCriterion.getLossRate();
        this.riskRewardRatio = kellyCriterion.getRiskRewardRatio();
        this.kellyCriterion = kellyCriterion.getKellyCriterion();
    }
}
