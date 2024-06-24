package quixotic.projects.cryptomanager.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import quixotic.projects.cryptomanager.model.TokenTx;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArbitrumService {

//    private static final String BASE_URL = "https://api.arbiscan.io/api";
    private static final String BASE_URL = "https://api-optimistic.etherscan.io/api";
//    private static final String API_KEY = "VGT8MHGKBMVG4XX6M5NGR5QDZNIXF2RFN3";
    private static final String API_KEY = "AJQRMWNMB7CKZJ66741M4XTI38XKMTX42W";
    private final RestTemplate restTemplate;
    private ObjectMapper mapper = new ObjectMapper();

    public Map<String, BigDecimal> getWalletBalances(String address) {
        Set<TokenTx> coins = getCoinList(address);
        Map<String, BigDecimal> balances = new HashMap<>();

        for (TokenTx coin : coins) {
            String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                    .queryParam("module", "account")
                    .queryParam("action", "tokenbalance")
                    .queryParam("contractaddress", coin.getContractAddress())
                    .queryParam("address", address)
                    .queryParam("tag", "latest")
                    .queryParam("apikey", API_KEY)
                    .toUriString();

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && "1".equals(response.get("status"))) {
                String balance = (String) response.get("result");
                balances.put(coin.getTokenSymbol(), new BigDecimal(balance).divide(BigDecimal.valueOf(1e18))); // Convert Wei to Ether
            } else {
                balances.put(coin.getTokenSymbol(), BigDecimal.ZERO);
            }
        }

        return balances;
    }

    public Set<TokenTx> getCoinList(String address) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("module", "account")
                .queryParam("action", "tokentx")
                .queryParam("address", address)
                .queryParam("apikey", API_KEY)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "1".equals(response.get("status"))) {
            List<TokenTx> txs = mapper.convertValue(response.get("result"), new TypeReference<>() {});
            Map<String, TokenTx> uniqueContractAddressMap = txs.stream()
                    .collect(Collectors.toMap(
                            TokenTx::getContractAddress,
                            tokenTx -> tokenTx,
                            (existing, replacement) -> existing,
                            LinkedHashMap::new));

            return new HashSet<>(uniqueContractAddressMap.values());
        }
        return Set.of();
    }

    public BigDecimal getWalletBalance(String address) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("module", "account")
                .queryParam("action", "balance")
                .queryParam("address", address)
                .queryParam("tag", "latest")
                .queryParam("apikey", API_KEY)
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "1".equals(response.get("status"))) {
            String balance = (String) response.get("result");
            return new BigDecimal(balance).divide(BigDecimal.valueOf(1e18)); // Convert Wei to Ether
        }
        return BigDecimal.ZERO;
    }

    public Map<String, Object> getTransactions(String address) {
        String url = UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .queryParam("module", "account")
                .queryParam("action", "txlist")
                .queryParam("address", address)
                .queryParam("startblock", "0")
                .queryParam("endblock", "99999999")
                .queryParam("sort", "asc")
                .queryParam("apikey", API_KEY)
                .toUriString();

        return restTemplate.getForObject(url, Map.class);
    }
}
