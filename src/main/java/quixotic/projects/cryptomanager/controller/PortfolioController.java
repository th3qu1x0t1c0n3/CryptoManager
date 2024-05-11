package quixotic.projects.cryptomanager.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import quixotic.projects.cryptomanager.dto.TransactionDTO;
import quixotic.projects.cryptomanager.service.PortfolioService;

import java.util.List;

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

}
