package quixotic.projects.cryptomanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
