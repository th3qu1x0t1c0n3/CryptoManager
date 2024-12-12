package quixotic.projects.cryptomanager.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.chain.TokenTx;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenTxDTO {
    public String blockNumber;
    public String timeStamp;
    public String hash;
    public String nonce;
    public String blockHash;
    public String contractAddress;
    public String from;
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

    public TokenTxDTO(TokenTx tokenTx) {
        this.blockNumber = tokenTx.getBlockNumber();
        this.timeStamp = tokenTx.getTimeStamp();
        this.hash = tokenTx.getHash();
        this.nonce = tokenTx.getNonce();
        this.blockHash = tokenTx.getBlockHash();
        this.contractAddress = tokenTx.getContractAddress();
        this.from = tokenTx.getFrom();
        this.to = tokenTx.getTo();
        this.value = tokenTx.getValue();
        this.tokenName = tokenTx.getTokenName();
        this.tokenSymbol = tokenTx.getTokenSymbol();
        this.tokenDecimal = tokenTx.getTokenDecimal();
        this.transactionIndex = tokenTx.getTransactionIndex();
        this.gas = tokenTx.getGas();
        this.gasPrice = tokenTx.getGasPrice();
        this.gasUsed = tokenTx.getGasUsed();
        this.cumulativeGasUsed = tokenTx.getCumulativeGasUsed();
        this.input = tokenTx.getInput();
        this.confirmations = tokenTx.getConfirmations();
        this.gasPriceBid = tokenTx.getGasPriceBid();
        this.isError = tokenTx.getIsError();
        this.txreceipt_status = tokenTx.getTxreceipt_status();
        this.methodId = tokenTx.getMethodId();
        this.functionName = tokenTx.getFunctionName();
    }

}
