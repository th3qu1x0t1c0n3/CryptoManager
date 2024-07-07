package quixotic.projects.cryptomanager.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
