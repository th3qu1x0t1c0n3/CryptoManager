package quixotic.projects.cryptomanager.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cryptomanager.dto.KellyCriterionDTO;
import quixotic.projects.cryptomanager.dto.TransactionDTO;
import quixotic.projects.cryptomanager.service.PortfolioService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/port")
public class PortfolioController {
    private final PortfolioService portfolioService;

    //    Transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDTO>> getTransactions(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getTransactions(token));
    }
    @PostMapping("/transaction")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.createTransaction(transactionDTO, token));
    }
    @PutMapping("/transaction")
    public ResponseEntity<TransactionDTO> updateTransaction(@RequestBody TransactionDTO transactionDTO, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.updateTransaction(transactionDTO, token));
    }
    @DeleteMapping("/transaction")
    public ResponseEntity<Void> deleteTransaction(@PathParam("id") Long id, @RequestHeader("Authorization") String token) {
        portfolioService.deleteTransaction(id, token);
        return ResponseEntity.noContent().build();
    }

//    Balance
    @GetMapping("/balance")
    public ResponseEntity<Map<String, Double>> getTransactionsByUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getCoinBalances(token));
    }
    @GetMapping("/balance/{coin}")
    public ResponseEntity<Double> getCoinBalance(@PathVariable String coin, @RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getCoinBalance(coin, token));
    }

    @GetMapping("/kellyCriterion")
    public ResponseEntity<KellyCriterionDTO> getKellyCriterion(@RequestHeader("Authorization") String token) {
        return ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON)
                .body(portfolioService.getKellyCriterion(token));
    }

}
