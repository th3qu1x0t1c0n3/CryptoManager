package quixotic.projects.cryptomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
