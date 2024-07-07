package quixotic.projects.cryptomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.Token;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllByUser_Email(String email);
}
