package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.Allocation;
import quixotic.projects.cryptomanager.model.User;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AllocationDTO {
    private Long id;
    private String coin;
    private double percentage;
    private double allocation;
    private double currentAllocation;
    private Long userId;

    public AllocationDTO(Allocation allocation) {
        this.id = allocation.getId();
        this.coin = allocation.getCoin();
        this.percentage = allocation.getPercentage();
        this.allocation = allocation.getAllocation();
        this.currentAllocation = allocation.getCurrentAllocation();
        this.userId = allocation.getUser().getId();
    }

    public Allocation toAllocation(User user) {
        return Allocation.builder()
                .id(this.id)
                .coin(this.coin)
                .percentage(this.percentage)
                .allocation(user.getPortfolioSize() * (this.percentage / 100))
                .currentAllocation(this.currentAllocation)
                .user(user)
                .build();
    }
}
