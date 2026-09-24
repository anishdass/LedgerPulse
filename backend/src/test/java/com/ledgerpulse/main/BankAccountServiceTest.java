package com.ledgerpulse.main;

import com.ledgerpulse.main.models.BankAccount;
import com.ledgerpulse.main.models.enums.CurrencyCode;
import com.ledgerpulse.main.repositories.BankAccountRepository;
import com.ledgerpulse.main.services.BankAccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {
    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountService bankAccountService;

    @Test
    void shouldCreateBankAccount() {
        BankAccount account = new BankAccount("John Doe", new BigDecimal("1000.00"), CurrencyCode.USD);
        when(bankAccountRepository.save(any(BankAccount.class))).thenReturn(account);

        BankAccount createdAccount = bankAccountService.createBankAccount(
                "John Doe",
                new BigDecimal("1000.00"),
                CurrencyCode.USD
        );

        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getBalance()).isEqualByComparingTo(new BigDecimal("1000.00"));
        verify(bankAccountRepository, times(1)).save(any(BankAccount.class));
    }
}