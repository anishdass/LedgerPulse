package com.ledgerpulse.main.models;

import com.ledgerpulse.main.models.enums.CurrencyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts")
@NoArgsConstructor
@Getter
public class BankAccount {
    @Id
    private final UUID id = UUID.randomUUID();

    @NotBlank(message = "Account holder name cannot be empty")
    private String accountHolderName;

    @NotNull(message = "Balance cannot be null")
    @DecimalMin(value = "0.0", message = "Balance cannot be negative initially")
    private BigDecimal balance = BigDecimal.ZERO;

    @NotNull(message = "Currency code cannot be blank")
    private CurrencyCode currencyCode;

    public BankAccount(String accountHolderName, BigDecimal balance, CurrencyCode currencyCode) {
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.currencyCode = currencyCode;
    }
}
