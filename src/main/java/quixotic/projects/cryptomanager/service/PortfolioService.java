package quixotic.projects.cryptomanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cryptomanager.dto.WalletDTO;
import quixotic.projects.cryptomanager.dto.chain.TokenDTO;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.model.chain.Token;
import quixotic.projects.cryptomanager.repository.AllocationRepository;
import quixotic.projects.cryptomanager.repository.UserRepository;
import quixotic.projects.cryptomanager.repository.WalletRepository;
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


    //   Balances
    public List<TokenDTO> getTokenBalancesByUser(String token) {
        String username = jwtTokenProvider.getUsernameFromJWT(token);
        List<Token> tokens = tokenRepository.findAllByUser_Email(username);

        return tokens.stream().map(TokenDTO::new).toList();
    }
}
