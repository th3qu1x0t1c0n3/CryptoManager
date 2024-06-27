package quixotic.projects.cryptomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.TokenTx;

import java.util.List;
import java.util.Optional;

public interface TokenTxRepository extends JpaRepository<TokenTx, String> {
    List<TokenTx> findAllByUser_Email(String email);
}
