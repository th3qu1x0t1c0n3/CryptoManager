package quixotic.projects.cryptomanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Transfer {
    @Id
    private String transactionHash;
    private String contractAddress;
    private String from;
    private String to;
    private BigDecimal value;

    public void addValue(BigDecimal value) {
        this.value = this.value.add(value);
    }

    public void subtractValue(BigDecimal value) {
        this.value = this.value.subtract(value);
    }
}
