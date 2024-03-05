package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	private CommandValidator commandValidator;
	private Bank bank;
	private InputParser inputParser;

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
}
