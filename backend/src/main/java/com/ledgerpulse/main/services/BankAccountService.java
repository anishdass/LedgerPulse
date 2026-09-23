package com.ledgerpulse.main.services;

import com.ledgerpulse.main.models.BankAccount;
import com.ledgerpulse.main.models.enums.CurrencyCode;
import com.ledgerpulse.main.repositories.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccount createBankAccount(String accountHolderName, java.math.BigDecimal initialBalance, CurrencyCode currencyCode) {
        BankAccount bankAccount = new BankAccount(accountHolderName, initialBalance, currencyCode);
        return bankAccountRepository.save(bankAccount);
    }

}
