package com.ledgerpulse.main;

import org.springframework.boot.SpringApplication;

public class TestLedgerPulseApplication {

	public static void main(String[] args) {
		SpringApplication.from(LedgerPulseApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
