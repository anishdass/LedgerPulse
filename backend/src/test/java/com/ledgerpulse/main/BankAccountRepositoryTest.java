package com.ledgerpulse.main;

import com.ledgerpulse.main.models.BankAccount;
import com.ledgerpulse.main.models.enums.CurrencyCode;
import com.ledgerpulse.main.repositories.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class BankAccountRepositoryTest {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    void shouldSaveAndFindBankAccountByAccountNumber() {
//        Given
        BankAccount account = new BankAccount("John Doe", new BigDecimal("1000.00"), CurrencyCode.USD);

//        When
        bankAccountRepository.save(account);
        Optional<BankAccount> foundAccount = bankAccountRepository.findByAccountNumber(account.getAccountNumber());

//        Then
        assertThat(foundAccount).isPresent();
        assertThat(foundAccount.get().getAccountNumber()).isEqualTo(account.getAccountNumber());
    }
}
