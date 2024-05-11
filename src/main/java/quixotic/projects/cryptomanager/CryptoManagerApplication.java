package quixotic.projects.cryptomanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import quixotic.projects.cryptomanager.dto.CoinTransactionDTO;
import quixotic.projects.cryptomanager.dto.SignUpDTO;
import quixotic.projects.cryptomanager.dto.TransactionDTO;
import quixotic.projects.cryptomanager.service.PortfolioService;
import quixotic.projects.cryptomanager.service.UserService;

import java.time.LocalDate;

@SpringBootApplication
public class CryptoManagerApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;
    @Autowired
    private PortfolioService portfolioService;

    public static void main(String[] args) {
        SpringApplication.run(CryptoManagerApplication.class, args);
    }

    @Override
    public void run(String... args) {
        createUsers();
        createTransactions();
    }

    private void createUsers() {
        userService.createCook(SignUpDTO.builder()
                .email("whale@gmail.com")
                .password("Password123")
                .firstName("Crypto")
                .lastName("Whale")
                .build());
    }

    private void createTransactions() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ3aGFsZUBnbWFpbC5jb20iLCJpYXQiOjE3MTUzOTgzNjksImV4cCI6MTcxNTQ4NDc2OSwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IlVTRVIifV19.JiBXX2lkbWNchoiK2G4Cs9L24h6Y8Mm0AxJLvVh8uNw";

        portfolioService.createTransaction(TransactionDTO.builder()
                .toCoin(CoinTransactionDTO.builder()
                        .name("BTC")
                        .quantity(0.5)
                        .value(25000.0)
                        .build()
                )
                .fromCoin(CoinTransactionDTO.builder()
                        .name("CAD")
                        .quantity(25000.0)
                        .value(25000.0)
                        .build()
                )
                .transactionDate(LocalDate.parse("2022-12-01"))
                .wallet("MyWallet")
                .exchange("Newton")
                .build(), token);

        portfolioService.createTransaction(TransactionDTO.builder()
                .toCoin(CoinTransactionDTO.builder()
                        .name("BTC")
                        .quantity(0.5)
                        .value(25000.0)
                        .build()
                )
                .fromCoin(CoinTransactionDTO.builder()
                        .name("CAD")
                        .quantity(25000.0)
                        .value(25000.0)
                        .build()
                )
                .transactionDate(LocalDate.parse("2022-12-02"))
                .wallet("MyWallet")
                .exchange("Newton")
                .build(), token);

        portfolioService.createTransaction(TransactionDTO.builder()
                .toCoin(CoinTransactionDTO.builder()
                        .name("BTC")
                        .quantity(0.5)
                        .value(25000.0)
                        .build()
                )
                .fromCoin(CoinTransactionDTO.builder()
                        .name("CAD")
                        .quantity(25000.0)
                        .value(25000.0)
                        .build()
                )
                .transactionDate(LocalDate.parse("2022-12-03"))
                .wallet("MyWallet")
                .exchange("Newton")
                .build(), token);

        portfolioService.createTransaction(TransactionDTO.builder()
                .toCoin(CoinTransactionDTO.builder()
                        .name("CAD")
                        .quantity(30000)
                        .value(30000.0)
                        .build()
                )
                .fromCoin(CoinTransactionDTO.builder()
                        .name("BTC")
                        .quantity(0.5)
                        .value(30000.0)
                        .build()
                )
//                .toCoin("CAD")
//                .toCoinQuantity(30000.0)
//                .toCoinValue(30000.0)
//                .fromCoin("BTC")
//                .fromCoinQuantity(0.5)
//                .fromCoinValue(30000.0)
                .transactionDate(LocalDate.parse("2022-12-03"))
                .wallet("MyWallet")
                .exchange("Newton")
                .build(), token);
    }

}
