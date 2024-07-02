package quixotic.projects.cryptomanager.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import quixotic.projects.cryptomanager.dto.EtherPriceDTO;
import quixotic.projects.cryptomanager.dto.TokenDTO;
import quixotic.projects.cryptomanager.dto.TokenTxDTO;
import quixotic.projects.cryptomanager.dto.WalletDTO;
import quixotic.projects.cryptomanager.model.Network;
import quixotic.projects.cryptomanager.model.Transfer;
import quixotic.projects.cryptomanager.service.EtherService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/ether")
public class EtherController {
    private final EtherService etherService;

    @GetMapping("/price")
    public ResponseEntity<EtherPriceDTO> getEthPrice(@PathParam("network") Network network) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(etherService.getEthPrice(WalletDTO.builder().network(network).build()));
    }

    //    Blockchains
    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> getEthBalance(@PathParam("address") String address, @PathParam("network") Network network) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(etherService.getWalletBalance(WalletDTO.builder().address(address).network(network).build()));
    }

    @GetMapping("/balances")
    public ResponseEntity<Set<TokenDTO>> getFullBalance(@PathParam("address") String address, @PathParam("network") Network network) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(etherService.getWalletBalances(WalletDTO.builder().address(address).network(network).build()));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TokenTxDTO>> getTransactions(@PathParam("address") String address, @PathParam("network") Network network) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(etherService.getTransactions(WalletDTO.builder().address(address).network(network).build(), null));
    }

    @GetMapping("/transactions/{contractAddress}")
    public ResponseEntity<List<TokenTxDTO>> getTransactionsByContractAddress(@PathVariable String contractAddress, @PathParam("address") String address, @PathParam("network") Network network) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(etherService.getTransactionsByContractAddress(WalletDTO.builder().address(address).network(network).build(), contractAddress));
    }

    @GetMapping("/transaction/{hash}")
    public ResponseEntity<TokenTxDTO> getTransaction(@PathVariable String hash, @PathParam("address") String address, @PathParam("network") Network network) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(etherService.getTransactionDetails(WalletDTO.builder().address(address).network(network).build(), hash));
    }

    @GetMapping("/transaction/receipt/{hash}")
    public ResponseEntity<Map<String, List<Transfer>>> getTransactionReceipt(@PathVariable String hash, @PathParam("address") String address, @PathParam("network") Network network) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(etherService.getTxByReceipt(WalletDTO.builder().address(address).network(network).build(), hash));
    }

}
