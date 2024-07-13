package quixotic.projects.cryptomanager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private User user;

    private String blockNumber;
    private String timeStamp;
    @Id
    private String hash;
    private String nonce;
    private String blockHash;
    private String contractAddress;
    @Column(name = "`from`")
    private String from;
    @Column(name = "`to`")
    private String to;
    private String value;
    private String tokenName;
    private String tokenSymbol;
    private String tokenDecimal;
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


    public void update(TokenTx tx) {
        // hash is not updated
        if (tx.user != null) {
            this.user = tx.user;
        }
        if (tx.blockNumber != null && !tx.blockNumber.isEmpty()) {
            this.blockNumber = tx.blockNumber;
        }
        if (tx.timeStamp != null && !tx.timeStamp.isEmpty()) {
            this.timeStamp = tx.timeStamp;
        }
        if (tx.nonce != null && !tx.nonce.isEmpty()) {
            this.nonce = tx.nonce;
        }
        if (tx.blockHash != null && !tx.blockHash.isEmpty()) {
            this.blockHash = tx.blockHash;
        }
        if (tx.contractAddress != null && !tx.contractAddress.isEmpty()) {
            this.contractAddress = tx.contractAddress;
        }
        if (tx.from != null && !tx.from.isEmpty()) {
            this.from = tx.from;
        }
        if (tx.to != null && !tx.to.isEmpty()) {
            this.to = tx.to;
        }
        if (tx.value != null && !tx.value.isEmpty()) {
            this.value = tx.value;
        }
        if (tx.tokenName != null && !tx.tokenName.isEmpty()) {
            this.tokenName = tx.tokenName;
        }
        if (tx.tokenSymbol != null && !tx.tokenSymbol.isEmpty()) {
            this.tokenSymbol = tx.tokenSymbol;
        }
        if (tx.tokenDecimal != null && !tx.tokenDecimal.isEmpty()) {
            this.tokenDecimal = tx.tokenDecimal;
        }
        if (tx.transactionIndex != null && !tx.transactionIndex.isEmpty()) {
            this.transactionIndex = tx.transactionIndex;
        }
        if (tx.gas != null && !tx.gas.isEmpty()) {
            this.gas = tx.gas;
        }
        if (tx.gasPrice != null && !tx.gasPrice.isEmpty()) {
            this.gasPrice = tx.gasPrice;
        }
        if (tx.gasUsed != null && !tx.gasUsed.isEmpty()) {
            this.gasUsed = tx.gasUsed;
        }
        if (tx.cumulativeGasUsed != null && !tx.cumulativeGasUsed.isEmpty()) {
            this.cumulativeGasUsed = tx.cumulativeGasUsed;
        }
        if (tx.input != null && !tx.input.isEmpty()) {
            this.input = tx.input;
        }
        if (tx.confirmations != null && !tx.confirmations.isEmpty()) {
            this.confirmations = tx.confirmations;
        }
        if (tx.gasPriceBid != null && !tx.gasPriceBid.isEmpty()) {
            this.gasPriceBid = tx.gasPriceBid;
        }
        if (tx.isError != null && !tx.isError.isEmpty()) {
            this.isError = tx.isError;
        }
        if (tx.txreceipt_status != null && !tx.txreceipt_status.isEmpty()) {
            this.txreceipt_status = tx.txreceipt_status;
        }
        if (tx.methodId != null && !tx.methodId.isEmpty()) {
            this.methodId = tx.methodId;
        }
        if (tx.functionName != null && !tx.functionName.isEmpty()) {
            this.functionName = tx.functionName;
        }
        if (tx.maxFeePerGas != null && !tx.maxFeePerGas.isEmpty()) {
            this.maxFeePerGas = tx.maxFeePerGas;
        }
        if (tx.maxPriorityFeePerGas != null && !tx.maxPriorityFeePerGas.isEmpty()) {
            this.maxPriorityFeePerGas = tx.maxPriorityFeePerGas;
        }
        if (tx.type != null && !tx.type.isEmpty()) {
            this.type = tx.type;
        }
        if (tx.chainId != null && !tx.chainId.isEmpty()) {
            this.chainId = tx.chainId;
        }
        if (tx.v != null && !tx.v.isEmpty()) {
            this.v = tx.v;
        }
        if (tx.r != null && !tx.r.isEmpty()) {
            this.r = tx.r;
        }
        if (tx.s != null && !tx.s.isEmpty()) {
            this.s = tx.s;
        }
        if (tx.yParity != null && !tx.yParity.isEmpty()) {
            this.yParity = tx.yParity;
        }
    }
}
