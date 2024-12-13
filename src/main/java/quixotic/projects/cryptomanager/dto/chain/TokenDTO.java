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
    public String user;
    public CurrencyDTO currency;

    public TokenDTO(Token token) {
        this.balance = token.getBalance();
        this.user = token.getUser().getUsername();
        this.currency = new CurrencyDTO(token.getCurrency());
    }

    public Token toEntity(User user) {
        return Token.builder()
                .balance(this.balance)
                .currency(this.currency.toEntity())
                .user(user)
                .build();
    }
}
