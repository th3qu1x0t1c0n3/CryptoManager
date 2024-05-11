package quixotic.projects.cryptomanager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import quixotic.projects.cryptomanager.dto.TransactionDTO;
import quixotic.projects.cryptomanager.exception.badRequestException.BadRequestException;
import quixotic.projects.cryptomanager.model.Transaction;
import quixotic.projects.cryptomanager.model.User;
import quixotic.projects.cryptomanager.repository.TransactionRepository;
import quixotic.projects.cryptomanager.repository.UserRepository;
import quixotic.projects.cryptomanager.security.JwtTokenProvider;

import java.util.List;

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
}
