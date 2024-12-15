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
    @ManyToOne
    private Currency currency;
    @ManyToOne
    private User user;
    @Column(name = "`from`")
    private String from;
    @Column(name = "`to`")
    private String to;

    @Id
    private String hash;
    private String nonce;
    private String blockHash;
    private LocalDateTime timeStamp;
    private String value;
    private String transactionIndex;
    private String gas;
    private String gasPrice;
    private String gasUsed;
    private String cumulativeGasUsed;
    @Column(length = 10000)
    private String input;
    private String confirmations;
    //    ---
    private String gasPriceBid;
    private String isError;
    private String txreceipt_status;
    private String methodId;
    private String functionName;
    //   ---
    private String accessList;
    private String maxFeePerGas;
    private String maxPriorityFeePerGas;
    private String type;
    private String chainId;
    private String v;
    private String r;
    private String s;
    private String yParity;

}
