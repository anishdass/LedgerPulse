package com.ledgerpulse.main.services;

import com.ledgerpulse.main.models.BankAccount;
import com.ledgerpulse.main.repositories.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LedgerTransactionService {

    private final BankAccountRepository bankAccountRepository;

    public void transferFunds(String sourceAccountId, String targetAccountId, BigDecimal amount) {

        BankAccount sourceAccount = bankAccountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Source account not found"));
        BankAccount targetAccount = bankAccountRepository.findById(targetAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Target account not found"));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in source account");
        }

        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        targetAccount.setBalance(targetAccount.getBalance().add(amount));

        bankAccountRepository.save(sourceAccount);
        bankAccountRepository.save(targetAccount);
    }
}
