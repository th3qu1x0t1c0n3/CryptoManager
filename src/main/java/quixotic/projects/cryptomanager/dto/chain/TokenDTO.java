package quixotic.projects.cryptomanager.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.chain.Token;
import quixotic.projects.cryptomanager.model.User;

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
    public String user;

    public TokenDTO(Token token) {
        this.balance = token.getBalance();
        this.tokenName = token.getTokenName();
        this.tokenSymbol = token.getTokenSymbol();
        this.contractAddress = token.getContractAddress();
        this.tokenDecimal = token.getTokenDecimal();
        this.user = token.getUser().getUsername();
    }

    public Token toEntity(User user) {
        return Token.builder()
                .balance(this.balance)
                .tokenName(this.tokenName)
                .tokenSymbol(this.tokenSymbol)
                .contractAddress(this.contractAddress)
                .tokenDecimal(this.tokenDecimal)
                .user(user)
                .build();
    }
}
