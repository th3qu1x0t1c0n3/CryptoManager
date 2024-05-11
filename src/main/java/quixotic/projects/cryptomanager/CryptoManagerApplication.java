package quixotic.projects.cryptomanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quixotic.projects.cryptomanager.dto.SignUpDTO;
import quixotic.projects.cryptomanager.outsource.UserService;

@SpringBootApplication
public class CryptoManagerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CryptoManagerApplication.class, args);
	}

	@Autowired
	private UserService userService;

	@Override
	public void run(String... args) throws Exception {
		createUsers();
	}

	private void createUsers() {
		userService.createCook(SignUpDTO.builder()
				.email("whale@gmail.com")
				.password("Password123")
				.firstName("Crypto")
				.lastName("Whale")
				.build());
	}

}
