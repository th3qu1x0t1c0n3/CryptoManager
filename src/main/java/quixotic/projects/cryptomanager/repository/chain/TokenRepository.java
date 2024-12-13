package quixotic.projects.cryptomanager.repository.chain;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.chain.Token;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser_Username(String username);
}
