package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositCommandValidatorTest {
	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void invalid_empty_command_test() {
		String[] commandArgs = InputParser.parseCommand("");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_account_id_and_deposit_amount_test() {
		String[] commandArgs = InputParser.parseCommand("deposit");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_deposit_amount_test() {
		String[] commandArgs = InputParser.parseCommand("deposit 12345678");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_id_does_not_exist_test() {
		String[] commandArgs = InputParser.parseCommand("deposit 98765432 100");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_account_id_less_than_eight_digits_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 123456789 100");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_account_id_greater_than_eight_digits_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 123456789 100");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_account_id_not_numeric_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 123abcd8 2000");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_deposit_in_cd_accounts_test() {
		bank.openCDAccount("12345678", 0.1, 1000);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 100");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_zero_amount_into_savings_test() {
		bank.openSavingsAccount("12345678", 500);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_one_dollar_into_savings_test() {
		bank.openSavingsAccount("12345678", 0.05);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_negative_amount_into_savings_test() {
		bank.openSavingsAccount("12345678", 0.58);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_thousand_into_savings_test() {
		bank.openSavingsAccount("12345678", 0.23);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_maximum_amount_into_savings_test() {
		bank.openSavingsAccount("12345678", 0.93);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 2500");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void invalid_deposit_exceeds_maximum_in_savings_test() {
		bank.openSavingsAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 2501");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_zero_into_checking_account_valid_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_one_in_checking_account_valid_test() {
		bank.openCheckingAccount("12345678", 0.93);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_negative_amount_in_checking_account_invalid_test() {
		bank.openCheckingAccount("12345678", 0.23);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void depositing_maximum_into_checking_account_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void deposit_exceeds_maximum_into_checking_account_test() {
		bank.openCheckingAccount("12345678", 0.93);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1001");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void deposit_with_special_symbol_invalid_test() {
		bank.openSavingsAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1,000");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void deposit_contains_letter_invalid_test() {
		bank.openSavingsAccount("12345678", 0.23);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 ksa");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void deposit_not_a_double_invalid_test() {
		bank.openSavingsAccount("12345678", 0.23);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 r");
		assertFalse(commandValidator.validate(commandArgs));
	}

}
