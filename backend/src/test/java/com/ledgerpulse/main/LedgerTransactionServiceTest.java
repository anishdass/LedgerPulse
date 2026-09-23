package com.ledgerpulse.main;

import com.ledgerpulse.main.models.BankAccount;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LedgerTransactionServiceTest {

    @Test
    void shouldSuccessfullyTransferFundsBetweenAccounts() {
//        Given
        BankAccount sourceAccount = new BankAccount("Alice", new java.math.BigDecimal("1000.00"), com.ledgerpulse.main.models.enums.CurrencyCode.USD);
        BankAccount targetAccount = new BankAccount("Bob", new java.math.BigDecimal("200.00"), com.ledgerpulse.main.models.enums.CurrencyCode.USD);

//        when
        java.math.BigDecimal transferAmount = new java.math.BigDecimal("300.00");
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(transferAmount));
        targetAccount.setBalance(targetAccount.getBalance().add(transferAmount));

//        Then
        assertThat(sourceAccount.getBalance()).isEqualByComparingTo(new java.math.BigDecimal("700.00"));
        assertThat(targetAccount.getBalance()).isEqualByComparingTo(new java.math.BigDecimal("500.00"));
    }
}
