package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.Token;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDTO {
    public BigDecimal balance;
    public String tokenName;
    public String tokenSymbol;
    public String contractAddress;
    public String tokenDecimal;

    public TokenDTO(Token token) {
        this.balance = token.getBalance();
        this.tokenName = token.getTokenName();
        this.tokenSymbol = token.getTokenSymbol();
        this.contractAddress = token.getContractAddress();
        this.tokenDecimal = token.getTokenDecimal();
    }

    public Token toEntity() {
        return Token.builder()
                .balance(this.balance)
                .tokenName(this.tokenName)
                .tokenSymbol(this.tokenSymbol)
                .contractAddress(this.contractAddress)
                .tokenDecimal(this.tokenDecimal)
                .build();
    }
}
