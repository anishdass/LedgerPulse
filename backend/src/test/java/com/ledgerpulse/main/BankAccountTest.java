package com.ledgerpulse.main;

import com.ledgerpulse.main.models.BankAccount;
import com.ledgerpulse.main.models.enums.CurrencyCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        if (factory != null) {
            factory.close();
        }
    }

    @Test
    void whenValidAccountDetails_thenNoValidationErrors() {
        BankAccount account = new BankAccount(
                "Alice Smith",
                new BigDecimal("150.00"),
                CurrencyCode.USD
        );

        Set<ConstraintViolation<BankAccount>> violations = validator.validate(account);

        assertTrue(violations.isEmpty(), "Valid account should not produce validation errors");
    }

    @Test
    void whenNegativeBalance_thenValidationFails() {
        BankAccount account = new BankAccount(
                "Bob Jones",
                new BigDecimal("-50.00"),
                CurrencyCode.USD
        );

        Set<ConstraintViolation<BankAccount>> violations = validator.validate(account);

        assertFalse(violations.isEmpty(), "Negative balance should trigger validation violation");
        assertEquals(1, violations.size());
        assertEquals("Balance cannot be negative initially", violations.iterator().next().getMessage());
    }

    @Test
    void whenBlankHolderName_thenValidationFails() {
        BankAccount account = new BankAccount(
                "",
                new BigDecimal("150.00"),
                CurrencyCode.USD
        );

        Set<ConstraintViolation<BankAccount>> violations = validator.validate(account);

        assertFalse(violations.isEmpty(), "Blank holder name should trigger validation violation");
    }
}
