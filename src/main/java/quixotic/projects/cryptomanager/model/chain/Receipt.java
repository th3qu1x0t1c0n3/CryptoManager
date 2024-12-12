package quixotic.projects.cryptomanager.model.chain;

import jakarta.persistence.*;
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
    @Column(name = "`from`")
    private String from;
    @Column(name = "`to`")
    private String to;
    private String gasUsed;
    private String gasUsedForL1;
    private String l1BlockNumber;
    private String logsBloom;
    private String status;
    @OneToMany
    private List<Log> logs;

}
