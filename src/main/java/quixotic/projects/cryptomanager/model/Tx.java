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
    @Column(name = "`from`")
    private String from;
    @Column(name = "`to`")
    private String to;
    private String contractAddressFrom;
    private String type;
    private String value;
    private LocalDateTime timeStamp;
// Need From Wallet with Currency & Amount
// Need To Wallet with Currency & Amount
//    Find Cost basis of Tx
//    Create Wallet Tx


//    Gas
    private String gas;
    private String gasPrice;
    private String gasUsed;
    private String gasPriceBid;
    private String cumulativeGasUsed;
    private String maxFeePerGas;
    private String maxPriorityFeePerGas;

//    Other
    private String nonce;
    private String blockHash;
    private String transactionIndex;
    @Column(length = 10000)
    private String input;
    private String confirmations;
    //    ---
    private String isError;
    private String txreceipt_status;
    private String methodId;
    private String functionName;
    //   ---
    private String accessList;
    private String chainId;
    private String v;
    private String r;
    private String s;
    private String yParity;

}
