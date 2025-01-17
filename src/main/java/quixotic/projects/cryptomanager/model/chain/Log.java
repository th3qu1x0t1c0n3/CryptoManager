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
public class Log {
    @Id
    private String transactionHash;
    private String address;
    @ElementCollection
    private List<String> topics;
    private String data;
    private String blockNumber;
    private String transactionIndex;
    private String blockHash;
    private String logIndex;
    private boolean removed;

}
