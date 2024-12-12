package quixotic.projects.cryptomanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cryptomanager.dto.chain.TokenDTO;
import quixotic.projects.cryptomanager.dto.WalletDTO;
import quixotic.projects.cryptomanager.service.PortfolioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/port")
public class PortfolioController {
    private final PortfolioService portfolioService;

    //    Wallets
    @GetMapping("/wallets")
    public ResponseEntity<List<WalletDTO>> getWallets(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getWallets(token));
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletDTO> createWallet(@RequestBody WalletDTO walletDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.createWallet(walletDTO, token));
    }

    //    Balance
    @GetMapping("/tokenBalances")
    public ResponseEntity<List<TokenDTO>> getTokenBalancesByUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getTokenBalancesByUser(token));
    }
}
