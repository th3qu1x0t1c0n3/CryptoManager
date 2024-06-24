package quixotic.projects.cryptomanager.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    @Id
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
    @Column(length = 10000)
    public String input;
    public String confirmations;
    //    ---
    public String gasPriceBid;
    public String isError;
    public String txreceipt_status;
    public String methodId;
    public String functionName;

    public void update(TokenTx tx) {
        // hash is not updated

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
    }
}
