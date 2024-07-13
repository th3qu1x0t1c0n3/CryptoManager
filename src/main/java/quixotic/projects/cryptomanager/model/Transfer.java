package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public Long id;

    private String transactionHash;
    private String contractAddress;
    @Column(name = "`from`")
    private String from;
    @Column(name = "`to`")
    private String to;
    private BigDecimal value;

    public void addValue(BigDecimal value) {
        this.value = this.value.add(value);
    }

    public void subtractValue(BigDecimal value) {
        this.value = this.value.subtract(value);
    }
}
