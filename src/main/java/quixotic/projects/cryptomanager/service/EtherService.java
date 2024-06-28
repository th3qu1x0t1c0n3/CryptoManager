package quixotic.projects.cryptomanager.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import quixotic.projects.cryptomanager.dto.EtherPriceDTO;
import quixotic.projects.cryptomanager.dto.TokenDTO;
import quixotic.projects.cryptomanager.dto.TokenTxDTO;
import quixotic.projects.cryptomanager.dto.WalletDTO;
import quixotic.projects.cryptomanager.model.TokenTx;
import quixotic.projects.cryptomanager.model.old.User;
import quixotic.projects.cryptomanager.repository.TokenRepository;
import quixotic.projects.cryptomanager.repository.TokenTxRepository;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EtherService {
    private final RestTemplate restTemplate;
    private final TokenTxRepository tokenTxRepository;
    private final TokenRepository tokenRepository;
    private ObjectMapper mapper = new ObjectMapper();

    public EtherPriceDTO getEthPrice(WalletDTO walletDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(walletDTO.getNetwork().getBaseUrl())
                .queryParam("module", "stats")
                .queryParam("action", "ethprice")
                .queryParam("apikey", walletDTO.getNetwork().getApiKey())
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "1".equals(response.get("status"))) {
            return mapper.convertValue(response.get("result"), new TypeReference<>() {
            });
        } else {
            return EtherPriceDTO.builder()
                    .ethbtc("-1")
                    .ethbtc_timestamp("0000000000")
                    .ethusd("-1")
                    .ethusd_timestamp("0000000000")
                    .build();
        }
    }

    public Set<TokenDTO> getWalletBalances(WalletDTO walletDTO) {
        Set<TokenTxDTO> coins = getCoinList(walletDTO);
        Set<TokenDTO> balances = new HashSet<>();

        for (TokenTxDTO coin : coins) {
            String url = UriComponentsBuilder.fromHttpUrl(walletDTO.getNetwork().getBaseUrl())
                    .queryParam("module", "account")
                    .queryParam("action", "tokenbalance")
                    .queryParam("contractaddress", coin.getContractAddress())
                    .queryParam("address", walletDTO.getAddress())
                    .queryParam("tag", "latest")
                    .queryParam("apikey", walletDTO.getNetwork().getApiKey())
                    .toUriString();

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            if (response != null && "1".equals(response.get("status"))) {
                String balance = (String) response.get("result");
                balances.add(TokenDTO.builder()
                        .balance(new BigDecimal(balance)) // .divide(BigDecimal.valueOf(1e18)) Convert Wei to Ether
                        .tokenName(coin.getTokenName())
                        .tokenSymbol(coin.getTokenSymbol())
                        .contractAddress(coin.getContractAddress())
                        .build());
            } else {
                balances.add(TokenDTO.builder()
                        .balance(null)
                        .tokenName(coin.getTokenName())
                        .tokenSymbol(coin.getTokenSymbol())
                        .contractAddress(coin.getContractAddress())
                        .build());
            }
        }

        return tokenRepository.saveAll(balances.stream().map(TokenDTO::toEntity).collect(Collectors.toSet())).stream().map(TokenDTO::new).collect(Collectors.toSet());
    }

    public Set<TokenTxDTO> getCoinList(WalletDTO walletDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(walletDTO.getNetwork().getBaseUrl())
                .queryParam("module", "account")
                .queryParam("action", "tokentx")
                .queryParam("address", walletDTO.getAddress())
                .queryParam("apikey", walletDTO.getNetwork().getApiKey())
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "1".equals(response.get("status"))) {
            List<TokenTx> txs = mapper.convertValue(response.get("result"), new TypeReference<>() {
            });
            Map<String, TokenTx> uniqueContractAddressMap = txs.stream()
                    .collect(Collectors.toMap(
                            TokenTx::getContractAddress,
                            tokenTx -> tokenTx,
                            (existing, replacement) -> existing,
                            LinkedHashMap::new));

            return new HashSet<>(uniqueContractAddressMap.values()).stream().map(TokenTxDTO::new).collect(Collectors.toSet());
        }
        return Set.of();
    }

    public BigDecimal getWalletBalance(WalletDTO walletDTO) {
        String url = UriComponentsBuilder.fromHttpUrl(walletDTO.getNetwork().getBaseUrl())
                .queryParam("module", "account")
                .queryParam("action", "balance")
                .queryParam("address", walletDTO.getAddress())
                .queryParam("tag", "latest")
                .queryParam("apikey", walletDTO.getNetwork().getApiKey())
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "1".equals(response.get("status"))) {
            String balance = (String) response.get("result");
            return new BigDecimal(balance).divide(BigDecimal.valueOf(1e18)); // Convert Wei to Ether
        }
        return BigDecimal.ZERO;
    }

    public List<TokenTxDTO> getTransactionsByContractAddress(WalletDTO walletDTO, String contractAddress) {
        String url = UriComponentsBuilder.fromHttpUrl(walletDTO.getNetwork().getBaseUrl())
                .queryParam("module", "account")
                .queryParam("action", "tokentx")
                .queryParam("contractaddress", contractAddress)
                .queryParam("address", walletDTO.getAddress())
                .queryParam("apikey", walletDTO.getNetwork().getApiKey())
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "1".equals(response.get("status"))) {
            List<TokenTx> txs = mapper.convertValue(response.get("result"), new TypeReference<>() {
            });
            return updateAll(txs, null).stream().map(TokenTxDTO::new).collect(Collectors.toList());
        }
        return List.of();
    }

    public List<TokenTxDTO> getTransactions(WalletDTO walletDTO, User user) {
        String url = UriComponentsBuilder.fromHttpUrl(walletDTO.getNetwork().getBaseUrl())
                .queryParam("module", "account")
                .queryParam("action", "txlist")
                .queryParam("address", walletDTO.getAddress())
                .queryParam("startblock", "0")
                .queryParam("endblock", "99999999")
                .queryParam("sort", "asc")
                .queryParam("apikey", walletDTO.getNetwork().getApiKey())
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "1".equals(response.get("status"))) {
            List<TokenTx> txs = mapper.convertValue(response.get("result"), new TypeReference<>() {
            });

            return updateAll(txs, user).stream().map(TokenTxDTO::new).collect(Collectors.toList());
        }
        return List.of();
    }

    public TokenTxDTO getTransactionDetails(WalletDTO walletDTO, String hash) {
        String url = UriComponentsBuilder.fromHttpUrl(walletDTO.getNetwork().getBaseUrl())
                .queryParam("module", "proxy")
                .queryParam("action", "eth_getTransactionByHash")
                .queryParam("txhash", hash)
                .queryParam("apikey", walletDTO.getNetwork().getApiKey())
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        assert response != null;
        TokenTx tx = mapper.convertValue(response.get("result"), TokenTx.class);
        return updateAll(List.of(tx), null).stream().map(TokenTxDTO::new).findFirst().orElse(null);
    }

    private List<TokenTx> updateAll(List<TokenTx> txs, User user) {
        List<TokenTx> updatedTxs = new ArrayList<>();
        for (TokenTx tx : txs) {
            tx.setUser(user);
            Optional<TokenTx> existingTx = tokenTxRepository.findById(tx.getHash());
            if (existingTx.isPresent()) {
                updatedTxs.add(existingTx.get());
                existingTx.get().update(tx);
            } else {
                updatedTxs.add(tx);
            }
        }
        return tokenTxRepository.saveAll(updatedTxs);
    }

}
