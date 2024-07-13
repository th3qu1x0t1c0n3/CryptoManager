# Service

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class ArbiscanService {

    @Value("${arbiscan.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> getTransactionReceipt(String txHash) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.arbiscan.io/api")
                .queryParam("module", "proxy")
                .queryParam("action", "eth_getTransactionReceipt")
                .queryParam("txhash", txHash)
                .queryParam("apikey", apiKey)
                .toUriString();

        return restTemplate.getForObject(url, Map.class);
    }

    public List<Map<String, Object>> parseLogs(List<Map<String, Object>> logs) {
        String transferTopic = "0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef";
        return logs.stream()
                .filter(log -> log.get("topics").toString().contains(transferTopic))
                .map(log -> {
                    String from = "0x" + log.get("topics").toString().substring(26, 66);
                    String to = "0x" + log.get("topics").toString().substring(90, 130);
                    String valueHex = log.get("data").toString();
                    double value = Long.parseLong(valueHex.substring(2), 16);
                    return Map.of(
                            "from", from,
                            "to", to,
                            "value", value,
                            "contractAddress", log.get("address")
                    );
                })
                .toList();
    }
}

# Controller
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ArbiscanController {

    @Autowired
    private ArbiscanService arbiscanService;

    @GetMapping("/transactions")
    public List<Map<String, Object>> getTokenTransfers(@RequestParam String txHash) {
        Map<String, Object> receipt = arbiscanService.getTransactionReceipt(txHash);
        List<Map<String, Object>> logs = (List<Map<String, Object>>) receipt.get("logs");
        return arbiscanService.parseLogs(logs);
    }
}
