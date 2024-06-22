package quixotic.projects.cryptomanager.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cryptomanager.dto.AllocationDTO;
import quixotic.projects.cryptomanager.dto.CoinDTO;
import quixotic.projects.cryptomanager.dto.KellyCriterionDTO;
import quixotic.projects.cryptomanager.dto.TransactionDTO;
import quixotic.projects.cryptomanager.exception.badRequestException.BadRequestException;
import quixotic.projects.cryptomanager.model.Allocation;
import quixotic.projects.cryptomanager.model.KellyCriterion;
import quixotic.projects.cryptomanager.model.Transaction;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.repository.AllocationRepository;
import quixotic.projects.cryptomanager.repository.TransactionRepository;
import quixotic.projects.cryptomanager.repository.UserRepository;
import quixotic.projects.cryptomanager.security.JwtTokenProvider;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ExcelHandler excelHandler = new ExcelHandler();
    private final AllocationRepository allocationRepository;

    //    Transactions CRUD
    public List<TransactionDTO> getTransactions(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

//        User user = userRepository.findByEmail(username).orElseThrow();
//        loadExcelTransactions(user);

        return transactionRepository.findByUserEmail(username).stream().map(TransactionDTO::new).toList();
    }

    @Transactional
    public TransactionDTO createTransaction(TransactionDTO transactionDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();

        Transaction transaction = transactionRepository.save(transactionDTO.toTransaction(user));
        List<Transaction> transactions = transactionRepository.findByUserEmail(username);

        excelHandler.writeTransactionsToExcel(transactions, user.getFirstName() + "_" + user.getLastName());

        if (!transaction.isBuy()) {
            getKellyCriterion(token, user);
        }
        updateAllocation(transaction);

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
                        .moneyInvested(getCoinAllocation(entry.getKey(), token))
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

    public Double getCoinAllocation(String coin, String token) {
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
            }
        }

        return totalValue / totalQuantity;
    }

//    Allocations
    public Double updatePortfolioSize(double portfolioSize, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        user.setPortfolioSize(portfolioSize);
        rebalanceAllocation(user);
        return userRepository.save(user).getPortfolioSize();
    }

    private void rebalanceAllocation(User user) {
        List<Allocation> allocations = allocationRepository.findAllocationsByUser_Email(user.getEmail());

        for (Allocation allocation : allocations) {
            allocation.setAllocation((allocation.getPercentage() / 100) * user.getPortfolioSize());
            allocationRepository.save(allocation);
        }
    }

    public List<AllocationDTO> getAllocationsByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        return allocationRepository.findAllocationsByUser_Email(username).stream().map(allocation -> {
            allocation.setCurrentAllocation(getCoinAllocation(allocation.getCoin(), token));
            return new AllocationDTO(allocation);
        }).toList();
    }

    public AllocationDTO createAllocation(AllocationDTO allocationDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();

        allocationDTO.setCurrentAllocation(getCoinAllocation(allocationDTO.getCoin(), token));

        return new AllocationDTO(allocationRepository.save(allocationDTO.toAllocation(user)));
    }

    public AllocationDTO updateAllocation(AllocationDTO allocationDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();
        Allocation allocation = allocationRepository.findById(allocationDTO.getId()).orElseThrow();
        if (!allocation.getUser().equals(user)) {
            throw new BadRequestException("Allocation does not belong to user");
        }

        allocationDTO.setCurrentAllocation(getCoinAllocation(allocationDTO.getCoin(), token));
        allocation.updateAllocation(allocationDTO.toAllocation(user));

        return new AllocationDTO(allocationRepository.save(allocationDTO.toAllocation(user)));
    }

    private void updateAllocation(Transaction transaction) {
        User user = transaction.getUser();

        allocationRepository.findAllocationsByUser_Email(user.getEmail()).stream().map(allocation -> {
            if (allocation.getCoin().equals(transaction.getToCoinName())) {
                allocation.setCurrentAllocation(allocation.getCurrentAllocation() + transaction.getToCoinValue());
            } else if (allocation.getCoin().equals(transaction.getFromCoinName())) {
                allocation.setCurrentAllocation(allocation.getCurrentAllocation() - transaction.getFromCoinValue());
            }
            return allocationRepository.save(allocation);
        }).toList();

        userRepository.save(user);
    }

    //    Kelly Criterion
    public KellyCriterionDTO getKellyCriterion(String token, User user) {
        if (user == null) {
            String username = jwtTokenProvider.getUsernameFromJWT(token);
            user = userRepository.findByEmail(username).orElseThrow();
        }
        List<Transaction> transactions = transactionRepository.findByUserEmail(user.getEmail()).stream().sorted(Comparator.comparing(Transaction::getTransactionDate)).toList();

        List<Transaction> buyTransactions = new java.util.ArrayList<>(transactions.stream()
                .filter(Transaction::isBuy)
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .toList());
        List<Transaction> sellTransactions = transactions.stream()
                .filter((transaction -> !transaction.isBuy()))
                .sorted(Comparator.comparing(Transaction::getTransactionDate))
                .toList();

        if (buyTransactions.isEmpty() || sellTransactions.isEmpty()) {
            throw new BadRequestException("No Buy or Sell transactions");
        }

        double totalProfits = 0.0;
        double totalLosses = 0.0;
        int totalWinsCount = 0;
        int totalLossCount = 0;

        for (Transaction sellTransaction : sellTransactions) {
            List<Transaction> buyTransactionsForSell = buyTransactions.stream()
                    .filter(buyTransaction -> buyTransaction.getToCoinName().equals(sellTransaction.getFromCoinName()) &&
                            buyTransaction.getTransactionDate().isBefore(sellTransaction.getTransactionDate()))
                    .sorted(Comparator.comparing(Transaction::getTransactionDate))
                    .toList();

            double coinBalance = sellTransaction.getFromCoinQuantity();
            boolean counted = false;

            for (Transaction buyTransaction : buyTransactionsForSell) {
                if (coinBalance <= 0) {
                    break;
                }

                double returns = sellTransaction.getToCoinValue() - (sellTransaction.getFromCoinQuantity() * buyTransaction.getToCoinUnitValue());
                double weightedReturns = returns;

                if (buyTransaction.getToCoinQuantity() / sellTransaction.getFromCoinQuantity() < 1) {
                    weightedReturns = returns * (buyTransaction.getToCoinQuantity() / sellTransaction.getFromCoinQuantity());
                }

                if (sellTransaction.getFromCoinUnitValue() > buyTransaction.getToCoinUnitValue()) {
                    totalProfits += weightedReturns;
                    if (!counted) {
                        totalWinsCount++;
                        counted = true;
                    }
                } else {
                    totalLosses -= weightedReturns;
                    if (!counted) {
                        totalLossCount++;
                        counted = true;
                    }
                }

                coinBalance -= buyTransaction.getToCoinQuantity();

                if (buyTransaction.getFromCoinQuantity() - sellTransaction.getToCoinQuantity() <= 0) {
                    buyTransactions.remove(buyTransaction);
                }
            }
        }

        double winRate = (double) totalWinsCount / sellTransactions.size();
        double lossRate = (double) totalLossCount / sellTransactions.size();

        KellyCriterion kellyCriterionObj = KellyCriterion.builder()
                .nbProfit(totalProfits)
                .nbLoss(totalLosses)
                .totalReturn(totalProfits - totalLosses)
                .totalWin(totalWinsCount)
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
//        loadAllocations();
    }

    private void loadAllocations(String token) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            List<Transaction> transactions = transactionRepository.findByUserEmail(user.getEmail());
            Map<String, Double> coinBalances = getCoinBalances(token);

            double totalValue = coinBalances.values().stream().mapToDouble(value -> value).sum();

            for (Map.Entry<String, Double> entry : coinBalances.entrySet()) {
                double percentage = entry.getValue() / totalValue;
                double allocation = user.getPortfolioSize() * percentage;
                double currentAllocation = getCoinAllocation(entry.getKey(), user.getEmail());

                user.addAllocation(Allocation.builder()
                        .coin(entry.getKey())
                        .percentage(percentage)
                        .allocation(allocation)
                        .currentAllocation(currentAllocation)
                        .user(user)
                        .build());
            }
            userRepository.save(user);
        }

    }
}
