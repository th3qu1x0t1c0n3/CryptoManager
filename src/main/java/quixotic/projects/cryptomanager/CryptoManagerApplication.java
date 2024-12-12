package quixotic.projects.cryptomanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quixotic.projects.cryptomanager.dto.SignUpDTO;
import quixotic.projects.cryptomanager.service.UserService;

@SpringBootApplication
public class CryptoManagerApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(CryptoManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
         createUsers();
    }

    private void createUsers() {
        userService.createUser(SignUpDTO.builder()
                .email("TesterTest@gmail.com")
                .password("Password123")
                .firstName("Tester")
                .lastName("Test")
                .build());

        userService.createUser(SignUpDTO.builder()
                .email("whale@gmail.com")
                .password("Password123")
                .firstName("Crypto")
                .lastName("Whale")
                .build());
    }

}
