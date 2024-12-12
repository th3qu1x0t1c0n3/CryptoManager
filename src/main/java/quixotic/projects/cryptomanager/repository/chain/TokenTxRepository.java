package quixotic.projects.cryptomanager.repository.chain;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.chain.TokenTx;

import java.util.List;

public interface TokenTxRepository extends JpaRepository<TokenTx, String> {
    List<TokenTx> findAllByUser_Email(String email);
}
