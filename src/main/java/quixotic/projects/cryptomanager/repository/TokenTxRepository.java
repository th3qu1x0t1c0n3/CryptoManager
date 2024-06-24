package quixotic.projects.cryptomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.TokenTx;

public interface TokenTxRepository extends JpaRepository<TokenTx, String> {

}
