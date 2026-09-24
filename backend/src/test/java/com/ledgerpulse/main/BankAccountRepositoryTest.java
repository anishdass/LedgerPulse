/**
 * Functionality:
 * Integration test class for verifying persistence and custom query behaviors of {@link BankAccountRepository}.
 *
 * Why is it required?
 * In automated TDD workflows, repository integration testing ensures that JPA mappings, database constraints,
 * schema configurations, and Spring Data custom query methods (like `findByAccountNumber`) execute accurately
 * against an actual relational database dialect inside an isolated Docker container rather than assuming mocks work.
 */
package com.ledgerpulse.main;

import com.ledgerpulse.main.models.BankAccount;
import com.ledgerpulse.main.models.enums.CurrencyCode;
import com.ledgerpulse.main.repositories.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class BankAccountRepositoryTest {

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("ledger_test_db")
            .withUsername("test_user")
            .withPassword("test_password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void shouldSaveAndFindBankAccountByAccountNumber() {
        // Given
        BankAccount account = new BankAccount("John Doe", new BigDecimal("1000.00"), CurrencyCode.USD);

        // When
        bankAccountRepository.save(account);
        Optional<BankAccount> foundAccount = bankAccountRepository.findByAccountNumber(account.getAccountNumber());

        // Then
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getAccountNumber()).isEqualTo(account.getAccountNumber());
    }
}