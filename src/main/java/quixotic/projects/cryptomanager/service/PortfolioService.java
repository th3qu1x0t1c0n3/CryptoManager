package quixotic.projects.cryptomanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cryptomanager.dto.KellyCriterionDTO;
import quixotic.projects.cryptomanager.dto.TransactionDTO;
import quixotic.projects.cryptomanager.exception.badRequestException.BadRequestException;
import quixotic.projects.cryptomanager.model.KellyCriterion;
import quixotic.projects.cryptomanager.model.Transaction;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.repository.TransactionRepository;
import quixotic.projects.cryptomanager.repository.UserRepository;
import quixotic.projects.cryptomanager.security.JwtTokenProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    //    Transactions CRUD
    public List<TransactionDTO> getTransactions(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        return transactionRepository.findByUserEmail(username).stream().map(TransactionDTO::new).toList();
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        return new TransactionDTO(transactionRepository.save(transactionDTO.toTransaction(user)));
    }

    public TransactionDTO updateTransaction(TransactionDTO transactionDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        Transaction transaction = transactionRepository.findById(transactionDTO.getId()).orElseThrow();
        if (!transaction.getUser().equals(user)) {
            throw new BadRequestException("Transaction does not belong to user");
        }

        transaction.updateTransaction(transactionDTO);

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
    public Map<String, Double> getCoinBalances(String token) {
        List<TransactionDTO> transactions = getTransactions(token);

        Map<String, Double> coinBalances = new HashMap<>();

        for (TransactionDTO transaction : transactions) {
            coinBalances.put(transaction.getToCoin(), coinBalances.getOrDefault(transaction.getToCoin(), 0.0) + transaction.getToCoinQuantity());
            coinBalances.put(transaction.getFromCoin(), coinBalances.getOrDefault(transaction.getFromCoin(), 0.0) - transaction.getFromCoinQuantity());
        }

        return coinBalances;
    }

    public Double getCoinBalance(String coin, String token) {
        List<TransactionDTO> transactions = getTransactions(token);

        double dollarAllocation = 0.0;

        for (TransactionDTO transaction : transactions) {
            if (transaction.getToCoin().equals(coin)) {
                dollarAllocation += transaction.getToCoinValue();
            } else if (transaction.getFromCoin().equals(coin)) {
                dollarAllocation -= transaction.getFromCoinValue();
            }
        }

        return dollarAllocation;
    }

    public double calculateKellyCriterion(double winRatio, double lossRatio, double winLossRatio) {
        return winRatio - (lossRatio / winLossRatio);
    }

    public KellyCriterionDTO getKellyCriterion(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        List<TransactionDTO> transactions = getTransactions(token);

        Map<String, Double[]> coinBuyData = new HashMap<>();
        for (TransactionDTO transaction : transactions) {
            if (coinBuyData.containsKey(transaction.getToCoin())) {
                Double[] data = coinBuyData.get(transaction.getToCoin());
                data[0] += transaction.getToCoinQuantity();
                data[1] += transaction.getToCoinValue();
            } else {
                coinBuyData.put(transaction.getToCoin(), new Double[]{transaction.getToCoinQuantity(), transaction.getToCoinValue()});
            }
        }

        double totalProfits = 0.0;
        double totalLosses = 0.0;
        int totalWins = 0;
        int totalLossCount = 0;

        for (TransactionDTO transaction : transactions) {
            if (coinBuyData.containsKey(transaction.getFromCoin())) {
                Double[] data = coinBuyData.get(transaction.getFromCoin());
                double averageBuyValue = data[1] / data[0];

                double profitLoss = transaction.getFromCoinValue() - averageBuyValue * transaction.getFromCoinQuantity();
                if (profitLoss > 0) {
                    totalProfits += profitLoss;
                    totalWins++;
                } else if (profitLoss < 0) {
                    totalLosses += Math.abs(profitLoss);
                    totalLossCount++;
                }
            }
        }

        double winRate = (double) totalWins / transactions.size();
        double lossRate = (double) totalLossCount / transactions.size();
        double profitLossRatio = totalProfits / totalLosses;
        double riskRewardRatio = winRate / lossRate;
        double kellyCriterion = calculateKellyCriterion(winRate, lossRate, riskRewardRatio);

        KellyCriterion kellyCriterionObj = new KellyCriterion(totalProfits, totalLosses, profitLossRatio, totalWins, totalLossCount, winRate, lossRate, riskRewardRatio, kellyCriterion);
        user.setKellyCriterion(kellyCriterionObj);
        userRepository.save(user);

        return new KellyCriterionDTO(kellyCriterionObj);
    }

}
