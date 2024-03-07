package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private CommandValidator commandValidator;
	private InputParser inputParser;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
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

	@Test
	void account_ID_does_not_exists() {
		String[] commandArgs = inputParser.parseCommand("deposit 98765432 100");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_account_ID_less_than_eight_digit() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = inputParser.parseCommand("deposit 123456789 100");
		assertFalse(commandValidator.validate(commandArgs));

	}

}
