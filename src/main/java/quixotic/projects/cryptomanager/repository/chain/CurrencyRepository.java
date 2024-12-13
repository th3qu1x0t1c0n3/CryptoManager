package quixotic.projects.cryptomanager.repository.chain;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.chain.Currency;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Optional<Currency> findByContractAddress(String contractAddress);
}
