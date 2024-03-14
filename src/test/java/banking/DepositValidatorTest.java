package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

	@Test
	void invalid_account_ID_greater_than_eight_digits() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = inputParser.parseCommand("deposit 123456789 100");
		assertFalse(commandValidator.validate(commandArgs));

	}

	@Test
	void invalid_account_ID_not_numbers() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = inputParser.parseCommand("deposit 123abcd8 2000");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_deposit_in_CD_accounts() {
		bank.openCDAccount("12345678", 0.1, 1000);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 100");
		assertFalse(commandValidator.validate(commandArgs));

	}

	@Test
	void depositing_amount_zero_into_savings() {
		bank.openSavingsAccount("12345678", 500);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_amount_one_into_savings() {
		bank.openSavingsAccount("12345678", 0.05);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_negative_amount_into_savings() {
		bank.openSavingsAccount("12345678", 0.58);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_amount_thousand_into_savings() {
		bank.openSavingsAccount("12345678", 0.23);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_maximum_amount_into_savings() {
		bank.openSavingsAccount("12345678", 0.93);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 2500");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_deposit_exceeds_maximum_amount_in_savings() {
		bank.openSavingsAccount("12345678", 0.1);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 2501");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_amount_zero_into_checking_account_is_valid() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_amount_one_in_checking_account_is_valid() {
		bank.openCheckingAccount("12345678", 0.93);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_negative_amount_in_checking_account_is_valid() {
		bank.openCheckingAccount("12345678", 0.23);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_maximum_amount_into_checking_account() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

}
