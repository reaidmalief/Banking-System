package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountCreationCommandValidatorTest {
	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void validation_fails_for_empty_command_test() {
		String[] commandArgs = InputParser.parseCommand("");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_without_an_ID_and_apr_test() {
		String[] commandArgs = InputParser.parseCommand("create checking");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_without_an_apr_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_fails_without_an_starting_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 0.1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_success_with_upper_case_account_type_test() {
		String[] commandArgs = InputParser.parseCommand("create CHECKING 12345678 0.1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_with_short_ID_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 1234567 0.1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_with_long_ID_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 123456789 0.1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_with_letters_in_ID_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 123abc789 0.1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void checking_account_creation_fails_with_duplicate_ID_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 0.1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void checking_account_creation_succeeds_with_unique_numeric_ID_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("create checking 13345678 0.1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void savings_account_creation_fails_with_duplicate_ID_test() {
		bank.openSavingsAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("create savings 12345678 0.1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void savings_account_creation_succeeds_with_unique_numeric_ID_test() {
		bank.openSavingsAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("create savings 12345638 0.1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_fails_with_duplicate_ID_test() {
		bank.openCDAccount("12345678", 0.01, 1000);
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 0.1 1000");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_succeeds_with_unique_numeric_ID_test() {
		bank.openCDAccount("12345678", 0.01, 1000);
		String[] commandArgs = InputParser.parseCommand("create cd 12345677 0.1 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_succeeds_with_zero_apr_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_succeeds_with_a_whole_number_apr_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_succeeds_with_max_ten_percent_apr_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 10");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_with_a_negative_apr_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_with_an_apr_over_ten_percent_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 11");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_creation_fails_with_non_numeric_APR_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 0.a");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_fails_with_zero_starting_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 0.99 0");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_fails_with_insufficient_starting_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 0.39 999");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_succeeds_with_minimum_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 0.23 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_succeeds_with_maximum_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 0.93 10000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_fails_with_maximum_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 1.09 10001");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_account_creation_fails_with_negative_starting_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 2.83 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void validation_fails_for_CD_with_non_numeric_starting_balance_test() {
		String[] commandArgs = InputParser.parseCommand("create cd 12345678 1.21 6b");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void validation_fails_for_incorrect_account_type_command_test() {
		String[] commandArgs = InputParser.parseCommand("create save 12345678 0.01");
		assertFalse(commandValidator.validate(commandArgs));
	}
}
