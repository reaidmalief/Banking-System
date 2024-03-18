package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WithdrawCommandValidatorTest {

	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void empty_command_is_invalid_test() {
		String[] commandArgs = InputParser.parseCommand("");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_account_id_and_amount_test() {
		String[] commandArgs = InputParser.parseCommand("withdraw");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_withdraw_amount_test() {
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_id_not_found_in_bank_test() {
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 400");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_id_too_short_is_invalid_test() {
		bank.openCheckingAccount("12345678", 3.12);
		String[] commandArgs = InputParser.parseCommand("withdraw 1234567 400");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void account_id_too_long_is_invalid_test() {
		bank.openCheckingAccount("12345678", 0.51);
		String[] commandArgs = InputParser.parseCommand("withdraw 123456789 400");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void non_numeric_account_id_is_invalid_test() {
		bank.openCheckingAccount("12345678", 0.12);
		String[] commandArgs = InputParser.parseCommand("withdraw 1abc5678 400");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void zero_dollar_withdrawal_from_savings_is_valid_test() {
		bank.openSavingsAccount("12345678", 0.41);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void one_dollar_withdrawal_from_savings_is_valid_test() {
		bank.openSavingsAccount("12345678", 0.14);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void negative_withdrawal_amount_from_savings_is_invalid_test() {
		bank.openSavingsAccount("12345678", 0.21);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void max_withdrawal_from_savings_is_valid_test() {
		bank.openSavingsAccount("12345678", 0.34);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void over_max_withdrawal_from_savings_is_invalid_test() {
		bank.openSavingsAccount("12345678", 0.19);
		bank.doDeposit("12345678", 500);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1001");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void more_than_one_withdrawal_from_savings_in_a_month_is_invalid_test() {
		bank.openSavingsAccount("12345678", 0.91);
		bank.doDeposit("12345678", 500);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 200");
		assertTrue(commandValidator.validate(commandArgs));
		commandArgs = InputParser.parseCommand("withdraw 8765321 300");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void withdrawal_post_one_month_from_savings_is_valid_test() {
		bank.openSavingsAccount("12345678", 1.13);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 200");
		assertTrue(commandValidator.validate(commandArgs));
		bank.getAccounts().get("12345678").setMonths(3);
		commandArgs = InputParser.parseCommand("withdraw 12345678 300");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void zero_dollar_withdrawal_from_checking_is_valid_test() {
		bank.openCheckingAccount("12345678", 1.1);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void one_dollar_withdrawal_from_checking_is_valid_test() {
		bank.openCheckingAccount("12345678", 0.91);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void negative_withdrawal_from_checking_is_invalid_test() {
		bank.openCheckingAccount("12345678", 0.12);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void max_withdrawal_from_checking_is_valid_test() {
		bank.openCheckingAccount("12345678", 0.31);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 400");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void over_max_withdrawal_from_checking_is_invalid_test() {
		bank.openCheckingAccount("12345678", 1.01);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 401");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void two_withdrawals_from_checking_in_a_month_is_valid_test() {
		bank.openCheckingAccount("12345678", 1.11);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 100");
		assertTrue(commandValidator.validate(commandArgs));
		commandArgs = InputParser.parseCommand("withdraw 12345678 100");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void cd_withdrawal_before_twelve_months_is_invalid_test() {
		bank.openCDAccount("12345678", 0.01, 1250);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1250");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void full_balance_cd_withdrawal_post_twelve_months_is_valid_test() {
		bank.openCDAccount("12345678", 0.01, 1250);
		bank.getAccounts().get("12345678").setMonths(12);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1250");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void zero_dollar_cd_withdrawal_post_twelve_months_is_invalid_test() {
		bank.openCDAccount("12345678", 0.1, 1250);
		bank.getAccounts().get("12345678").setMonths(12);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 0");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void one_dollar_less_than_full_balance_cd_withdrawal_post_twelve_months_is_invalid_test() {
		bank.openCDAccount("12345678", 0.1, 1250);
		bank.getAccounts().get("12345678").setMonths(12);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void negative_amount_cd_withdrawal_post_twelve_months_is_invalid_test() {
		bank.openCDAccount("12345678", 0.1, 1250);
		bank.getAccounts().get("12345678").setMonths(12);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void over_balance_cd_withdrawal_post_twelve_months_is_valid_test() {
		bank.openCDAccount("12345678", 0.1, 1250);
		bank.getAccounts().get("12345678").setMonths(12);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1250");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void withdrawal_amount_with_symbol_is_invalid_test() {
		bank.openSavingsAccount("12345678", 0.10);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 1,200");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void withdrawal_amount_not_double_is_invalid_test() {
		bank.openCheckingAccount("12345678", 0.50);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 abc");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void withdrawal_amount_with_letters_is_invalid_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 28ab");
		assertFalse(commandValidator.validate(commandArgs));
	}

}