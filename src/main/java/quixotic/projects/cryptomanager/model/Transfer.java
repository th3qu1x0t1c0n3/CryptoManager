package quixotic.projects.cryptomanager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfer {
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
