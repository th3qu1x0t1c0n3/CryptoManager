package quixotic.projects.cryptomanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cryptomanager.dto.CoinDTO;
import quixotic.projects.cryptomanager.dto.KellyCriterionDTO;
import quixotic.projects.cryptomanager.dto.TransactionDTO;
import quixotic.projects.cryptomanager.exception.badRequestException.BadRequestException;
import quixotic.projects.cryptomanager.model.KellyCriterion;
import quixotic.projects.cryptomanager.model.Transaction;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.repository.TransactionRepository;
import quixotic.projects.cryptomanager.repository.UserRepository;
import quixotic.projects.cryptomanager.security.JwtTokenProvider;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ExcelHandler excelHandler = new ExcelHandler();

    //    Transactions CRUD
    public List<TransactionDTO> getTransactions(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();

        loadExcelTransactions(user);

        return transactionRepository.findByUserEmail(username).stream().map(TransactionDTO::new).toList();
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();

        loadExcelTransactions(user);

        Transaction transaction = transactionRepository.save(transactionDTO.toTransaction(user));
        List<Transaction> transactions = transactionRepository.findByUserEmail(username);

        excelHandler.writeTransactionsToExcel(transactions, user.getFirstName() + "_" + user.getLastName());

        if (!transaction.isBuy()) {
            getKellyCriterion("", user);
        }

        return new TransactionDTO(transaction);
    }

    public TransactionDTO updateTransaction(TransactionDTO transactionDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        Transaction transaction = transactionRepository.findById(transactionDTO.getId()).orElseThrow();
        if (!transaction.getUser().equals(user)) {
            throw new BadRequestException("Transaction does not belong to user");
        }

        transaction.updateTransaction(transactionDTO.toTransaction(user));

        return new TransactionDTO(transactionRepository.save(transactionDTO.toTransaction(user)));
    }

    public void deleteTransaction(Long id, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        Transaction transaction = transactionRepository.findById(id).orElseThrow();
        if (!transaction.getUser().equals(user)) {
            throw new BadRequestException("Transaction does not belong to user");
        }

        transaction.setUser(null);
        transactionRepository.delete(transaction);
    }

    //   Balances
    public List<CoinDTO> getCoinBalancesByUser(String token) {

        return getCoinBalances(token).entrySet().stream()
                .map(entry -> CoinDTO.builder()
                        .name(entry.getKey())
                        .holdings(entry.getValue())
                        .avgPrice(getAveragePrice(entry.getKey(), token))
                        .totalValue(entry.getValue() * getAveragePrice(entry.getKey(), token))
                        .currentPrice(1.0)
                        .build())
                .toList();
    }

    public Map<String, Double> getCoinBalances(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        List<Transaction> transactions = transactionRepository.findByUserEmail(username);

        Map<String, Double> coinBalances = new HashMap<>();

        for (Transaction transaction : transactions) {
            coinBalances.put(transaction.getToCoinName(), coinBalances.getOrDefault(transaction.getToCoinName(), 0.0) + transaction.getToCoinQuantity());
            coinBalances.put(transaction.getFromCoinName(), coinBalances.getOrDefault(transaction.getFromCoinName(), 0.0) - transaction.getFromCoinQuantity());
        }
        return coinBalances;
    }

    public Double getCoinBalance(String coin, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        List<Transaction> transactions = transactionRepository.findByUserEmail(username);

        double dollarAllocation = 0.0;

        for (Transaction transaction : transactions) {
            if (transaction.getToCoinName().equals(coin)) {
                dollarAllocation += transaction.getToCoinValue();
            } else if (transaction.getFromCoinName().equals(coin)) {
                dollarAllocation -= transaction.getFromCoinValue();
            }
        }

        return dollarAllocation;
    }

    public Double getAveragePrice(String coin, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        List<Transaction> transactions = transactionRepository.findByUserEmail(username);

        double totalValue = 0.0;
        double totalQuantity = 0.0;

        for (Transaction transaction : transactions) {
            if (transaction.getToCoinName().equals(coin)) {
                totalValue += transaction.getToCoinValue();
                totalQuantity += transaction.getToCoinQuantity();
            } else if (transaction.getFromCoinName().equals(coin)) {
                totalValue -= transaction.getFromCoinValue();
                totalQuantity -= transaction.getFromCoinQuantity();
            }
        }

        return totalValue / totalQuantity;
    }

    //    Kelly Criterion
    public KellyCriterionDTO getKellyCriterion(String token, User user) {
        if (user == null) {
            String username = jwtTokenProvider.getUsernameFromJWT(token);
            user = userRepository.findByEmail(username).orElseThrow();
        }
        List<Transaction> transactions = transactionRepository.findByUserEmail(user.getEmail());

        List<Transaction> buyTransactions = transactions.stream()
                .filter(Transaction::isBuy)
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .toList();
        List<Transaction> sellTransactions = transactions.stream()
                .filter((transaction -> !transaction.isBuy()))
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .toList();

        if (buyTransactions.isEmpty() || sellTransactions.isEmpty()) {
            throw new BadRequestException("No Buy or Sell transactions");
        }

        double totalProfits = 0.0;
        double totalLosses = 0.0;
        int totalWins = 0;
        int totalLossCount = 0;

        for (Transaction buyTransaction : buyTransactions) {
            for (Transaction sellTransaction : sellTransactions) {
                if (buyTransaction.getToCoinName().equals(sellTransaction.getFromCoinName())) {
                    if (sellTransaction.getToCoinValue() > buyTransaction.getFromCoinValue()) {
                        totalProfits += sellTransaction.getToCoinValue() - buyTransaction.getFromCoinValue();
                        totalWins++;
                    } else {
                        totalLosses += buyTransaction.getFromCoinValue() - sellTransaction.getToCoinValue();
                        totalLossCount++;
                    }
                }
            }
        }

        double winRate = (double) totalWins / transactions.size();
        double lossRate = (double) totalLossCount / transactions.size();

        KellyCriterion kellyCriterionObj = KellyCriterion.builder()
                .nbProfit(totalProfits)
                .nbLoss(totalLosses)
                .totalReturn(totalProfits - totalLosses)
                .totalWin(totalWins)
                .totalLoss(totalLossCount)
                .winRate(winRate)
                .lossRate(lossRate)
                .build();

        user.setKellyCriterion(kellyCriterionObj);
        userRepository.save(user);

        return new KellyCriterionDTO(kellyCriterionObj);
    }

    private void loadExcelTransactions(User user) {
        excelHandler.readTransactionsFromExcel(user.getFirstName() + "_" + user.getLastName()).stream()
                .map(transaction -> {
                    transaction.setUser(user);
                    return transactionRepository.save(transaction);
                }).toList();
    }
}
