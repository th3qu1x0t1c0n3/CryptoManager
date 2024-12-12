package quixotic.projects.cryptomanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cryptomanager.dto.chain.TokenDTO;
import quixotic.projects.cryptomanager.dto.chain.TokenTxDTO;
import quixotic.projects.cryptomanager.dto.WalletDTO;
import quixotic.projects.cryptomanager.model.Allocation;
import quixotic.projects.cryptomanager.model.chain.Token;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.repository.*;
import quixotic.projects.cryptomanager.repository.chain.TokenRepository;
import quixotic.projects.cryptomanager.repository.chain.TokenTxRepository;
import quixotic.projects.cryptomanager.security.JwtTokenProvider;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final EtherService etherService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final AllocationRepository allocationRepository;
    private final WalletRepository walletRepository;
    private final TokenTxRepository tokenTxRepository;
    private final TokenRepository tokenRepository;

    //    Wallets
    public List<WalletDTO> getWallets(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();

        return user.getWallets().stream().map(WalletDTO::new).toList();
    }

    public WalletDTO createWallet(WalletDTO walletDTO, String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(username).orElseThrow();

        switch (walletDTO.getNetwork()) {
            case ETHEREUM, ARBITRUM, OPTIMISM -> {
                etherService.getTransactions(walletDTO, user);
                etherService.getWalletBalances(walletDTO, token);
            }
            case BITCOIN -> System.out.println("Bitcoin");
            case SOLANA -> System.out.println("Solana");
            case DOGECOIN -> System.out.println("Dogecoin");
            default -> throw new IllegalStateException("Unexpected value: " + walletDTO.getNetwork());
        }

        user.addWallet(walletDTO.toEntity());
        user = userRepository.save(user);

        return new WalletDTO(user.getWallets().get(user.getWallets().size() - 1));
    }

    //    Transactions CRUD
    public List<TokenTxDTO> getTransactions(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);

//        User user = userRepository.findByEmail(username).orElseThrow();
//        loadExcelTransactions(user);

        return tokenTxRepository.findAllByUser_Email(username).stream().map(TokenTxDTO::new).toList();
//        return transactionRepository.findByUserEmail(username).stream().map(TransactionDTO::new).toList();
    }


    //   Balances
    public List<TokenDTO> getTokenBalancesByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        List<Token> tokens = tokenRepository.findAllByUser_Email(username);

        return tokens.stream().map(TokenDTO::new).toList();
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

//    public List<AllocationDTO> getAllocationsByUser(String token) {
//        String username = jwtTokenProvider.getUsernameFromJWT(token);
//        return allocationRepository.findAllocationsByUser_Email(username).stream().map(allocation -> {
//            allocation.setCurrentAllocation(getCoinAllocation(allocation.getCoin(), token));
//            return new AllocationDTO(allocation);
//        }).toList();
//    }
//
//    public AllocationDTO createAllocation(AllocationDTO allocationDTO, String token) {
//        String username = jwtTokenProvider.getUsernameFromJWT(token);
//        User user = userRepository.findByEmail(username).orElseThrow();
//
//        allocationDTO.setCurrentAllocation(getCoinAllocation(allocationDTO.getCoin(), token));
//
//        return new AllocationDTO(allocationRepository.save(allocationDTO.toAllocation(user)));
//    }
//
//    public AllocationDTO updateAllocation(AllocationDTO allocationDTO, String token) {
//        String username = jwtTokenProvider.getUsernameFromJWT(token);
//        User user = userRepository.findByEmail(username).orElseThrow();
//        Allocation allocation = allocationRepository.findById(allocationDTO.getId()).orElseThrow();
//        if (!allocation.getUser().equals(user)) {
//            throw new BadRequestException("Allocation does not belong to user");
//        }
//
//        allocationDTO.setCurrentAllocation(getCoinAllocation(allocationDTO.getCoin(), token));
//        allocation.updateAllocation(allocationDTO.toAllocation(user));
//
//        return new AllocationDTO(allocationRepository.save(allocationDTO.toAllocation(user)));
//    }
//
//    private void updateAllocation(Transaction transaction) {
//        User user = transaction.getUser();
//
//        allocationRepository.findAllocationsByUser_Email(user.getEmail()).stream().map(allocation -> {
//            if (allocation.getCoin().equals(transaction.getToCoinName())) {
//                allocation.setCurrentAllocation(allocation.getCurrentAllocation() + transaction.getToCoinValue());
//            } else if (allocation.getCoin().equals(transaction.getFromCoinName())) {
//                allocation.setCurrentAllocation(allocation.getCurrentAllocation() - transaction.getFromCoinValue());
//            }
//            return allocationRepository.save(allocation);
//        }).toList();
//
//        userRepository.save(user);
//    }
}
