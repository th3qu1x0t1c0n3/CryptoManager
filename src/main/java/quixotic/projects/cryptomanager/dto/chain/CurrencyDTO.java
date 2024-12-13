package quixotic.projects.cryptomanager.dto.chain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import quixotic.projects.cryptomanager.model.chain.Currency;
import quixotic.projects.cryptomanager.model.enums.CurrencyType;
import quixotic.projects.cryptomanager.model.enums.Network;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyDTO {
    private CurrencyType type;
    private Network network;
    private String contractAddress;
    private String tokenName;
    private String tokenSymbol;
    private String tokenDecimal;

    public CurrencyDTO(Currency currency) {
        this.type = currency.getType();
        this.network = currency.getNetwork();
        this.contractAddress = currency.getContractAddress();
        this.tokenName = currency.getTokenName();
        this.tokenSymbol = currency.getTokenSymbol();
        this.tokenDecimal = currency.getTokenDecimal();
    }

    public Currency toEntity() {
        return Currency.builder()
                .type(this.type)
                .network(this.network)
                .contractAddress(this.contractAddress)
                .tokenName(this.tokenName)
                .tokenSymbol(this.tokenSymbol)
                .tokenDecimal(this.tokenDecimal)
                .build();
    }
}
