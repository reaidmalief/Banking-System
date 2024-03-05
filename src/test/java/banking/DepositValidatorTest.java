package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private CommandValidator commandValidator;
	private InputParser inputParser;

	@BeforeEach
	void setUp() {
		Bank bank = new Bank();
		commandValidator = new CommandValidator(bank);
		inputParser = new InputParser();
	}

	@Test
	void invalid_empty_command() {
		String[] commandArgs = inputParser.parseCommand("");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_account_ID_and_deposit_amount() {
		String[] commandArgs = inputParser.parseCommand("deposit");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_deposit_amount() {
		String[] commandArgs = inputParser.parseCommand("deposit 12345678");
		assertFalse(commandValidator.validate(commandArgs));
	}

}
