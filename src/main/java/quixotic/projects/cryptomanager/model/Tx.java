package quixotic.projects.cryptomanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.chain.Currency;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Tx {
//    Main of Tx
    @Id
    private String hash;
    @ManyToOne
    private Currency currency;
    @ManyToOne
    private User user;
    @ManyToOne
    private TxBalance fromBalance;
    @ManyToOne
    private TxBalance toBalance;
    private String type;
    private LocalDateTime timeStamp;

//    Gas
    private String gas;
    private String gasPrice;
    private String gasUsed;
    private String gasPriceBid;
    private String cumulativeGasUsed;
    private String maxFeePerGas;
    private String maxPriorityFeePerGas;
}
