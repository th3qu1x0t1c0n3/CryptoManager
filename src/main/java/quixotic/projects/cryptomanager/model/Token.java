package quixotic.projects.cryptomanager.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    public String balance;
    public String tokenName;
    public String tokenSymbol;
    public String tokenDecimal;
    public String contractAddress;
}
