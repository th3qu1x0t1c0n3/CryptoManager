package quixotic.projects.cryptomanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cryptomanager.dto.*;
import quixotic.projects.cryptomanager.dto.old.AllocationDTO;
import quixotic.projects.cryptomanager.dto.old.CoinDTO;
import quixotic.projects.cryptomanager.dto.old.KellyCriterionDTO;
import quixotic.projects.cryptomanager.dto.old.TransactionDTO;
import quixotic.projects.cryptomanager.service.PortfolioService;

import java.util.List;
import java.util.Map;

@Controller
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

    //    Transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<TokenTxDTO>> getTransactions(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getTransactions(token));
    }

    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO, @RequestHeader("Authorization") String token) {

        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.createTransaction(transactionDTO, token));
    }
//    @PutMapping("/transaction")
//    public ResponseEntity<TransactionDTO> updateTransaction(@RequestBody TransactionDTO transactionDTO, @RequestHeader("Authorization") String token) {
//        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
//                .body(portfolioService.updateTransaction(transactionDTO, token));
//    }
//    @DeleteMapping("/transaction")
//    public ResponseEntity<Void> deleteTransaction(@PathParam("id") Long id, @RequestHeader("Authorization") String token) {
//        portfolioService.deleteTransaction(id, token);
//        return ResponseEntity.noContent().build();
//    }

    //    Balance
    @GetMapping("/tokenBalances")
    public ResponseEntity<List<TokenDTO>> getTokenBalancesByUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getTokenBalancesByUser(token));
    }
    @GetMapping("/coinBalances")
    public ResponseEntity<List<CoinDTO>> getCoinBalancesByUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getCoinBalancesByUser(token));
    }

    @GetMapping("/balance")
    public ResponseEntity<Map<String, Double>> getBalanceByUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getCoinBalances(token));
    }

    @GetMapping("/balance/{coin}")
    public ResponseEntity<Double> getCoinBalance(@PathVariable String coin, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getCoinAllocation(coin, token));
    }

    @GetMapping("/averagePrice/{coin}")
    public ResponseEntity<Double> getAveragePrice(@PathVariable String coin, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getAveragePrice(coin, token));
    }

    @GetMapping("/allocations")
    public ResponseEntity<List<AllocationDTO>> getAllocations(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getAllocationsByUser(token));
    }

    @PostMapping("/allocation")
    public ResponseEntity<AllocationDTO> createAllocation(@RequestBody AllocationDTO allocationDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.createAllocation(allocationDTO, token));
    }

    @PutMapping("/allocation")
    public ResponseEntity<AllocationDTO> updateAllocation(@RequestBody AllocationDTO allocationDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.updateAllocation(allocationDTO, token));
    }

    @PutMapping("/size")
    public ResponseEntity<Double> updatePortfolioSize(@RequestBody Double size, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.updatePortfolioSize(size, token));
    }

    @GetMapping("/kellyCriterion")
    public ResponseEntity<KellyCriterionDTO> getKellyCriterion(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getKellyCriterion(token, null));
    }

}
