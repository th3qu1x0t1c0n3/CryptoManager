package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Allocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String coin;
    private double percentage;
    private double allocation;
    private double currentAllocation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    public void updateAllocation(Allocation allocation) {
        this.percentage = allocation.getPercentage();
        this.currentAllocation = allocation.getCurrentAllocation();
    }
}
