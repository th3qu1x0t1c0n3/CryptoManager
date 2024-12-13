package quixotic.projects.cryptomanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import quixotic.projects.cryptomanager.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);
}
