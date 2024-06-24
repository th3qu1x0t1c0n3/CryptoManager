package quixotic.projects.cryptomanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Network {
    BITCOIN("https://blockchain.info/q", ""),
    ETHEREUM("https://api.etherscan.io/api", "S4R8CY3J4X4UT4CV2SD1AT5CVUNDMHEYEW"),
    ARBITRUM("https://api.arbiscan.io/api", "VGT8MHGKBMVG4XX6M5NGR5QDZNIXF2RFN3"),
    OPTIMISM("https://api-optimistic.etherscan.io/api", "AJQRMWNMB7CKZJ66741M4XTI38XKMTX42W"),
    SOLANA("https://api.solscan.io/api", ""),
    DOGECOIN("https://dogechain.info", "62c6da236d834c7583a6d3dc4db627b9"),
    ;

    private final String baseUrl;
    private final String apiKey;
}
