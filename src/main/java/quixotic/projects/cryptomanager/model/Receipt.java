package quixotic.projects.cryptomanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Receipt {
    @Id
    private String transactionHash;
    private String transactionIndex;
    private String type;
    private String blockHash;
    private String blockNumber;
    private String contractAddress;
    private String cumulativeGasUsed;
    private String effectiveGasPrice;
    private String to;
    private String from;
    private String gasUsed;
    private String gasUsedForL1;
    private String l1BlockNumber;
    private String logsBloom;
    private String status;
    @OneToMany
    private List<Log> logs;

}
