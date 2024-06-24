package quixotic.projects.cryptomanager.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TokenTx {
    public String blockNumber;
    public String timeStamp;
    public String hash;
    public String nonce;
    public String blockHash;
    public String contractAddress;
    @Column(name = "`from`")
    public String from;
    @Column(name = "`to`")
    public String to;
    public String value;
    public String tokenName;
    public String tokenSymbol;
    public String tokenDecimal;
    public String transactionIndex;
    public String gas;
    public String gasPrice;
    public String gasUsed;
    public String cumulativeGasUsed;
    public String input;
    public String confirmations;
    //    ---
    public String gasPriceBid;
    public String isError;
    public String txreceipt_status;
    public String methodId;
    public String functionName;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
