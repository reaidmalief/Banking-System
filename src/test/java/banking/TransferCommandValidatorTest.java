package banking;

import static banking.BankTest.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransferCommandValidatorTest {

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
	void missing_both_accounts_for_transfer_test() {
		String[] commandArgs = InputParser.parseCommand("transfer");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_destination_account_test() {
		String[] commandArgs = InputParser.parseCommand("transfer 12345678");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void missing_transfer_amount_test() {
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 87654321");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void from_account_does_not_exist_test() {
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 87654321 300");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void to_account_does_not_exist_test() {
		bank.openCheckingAccount(ID, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 87654321 300");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void both_accounts_do_not_exist_test() {
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 87654321 300");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void transferring_to_same_account_is_invalid_test() {
		bank.openCheckingAccount(ID, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345678 300");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void zero_dollar_transfer_between_checking_accounts_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void one_dollar_transfer_between_checking_accounts_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void negative_amount_transfer_between_checking_accounts_is_invalid_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void max_four_hundred_dollar_transfer_between_checking_accounts_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 400");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void over_four_hundred_dollar_transfer_between_checking_accounts_is_invalid_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 401");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void zero_dollar_transfer_between_savings_accounts_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void one_dollar_transfer_between_savings_accounts_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void negative_amount_transfer_between_savings_accounts_is_invalid_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void max_thousand_dollar_transfer_between_savings_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void over_thousand_dollar_transfer_between_savings_is_invalid_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1001");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void zero_dollar_transfer_from_checking_to_savings_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void one_dollar_transfer_from_checking_to_savings_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void negative_amount_transfer_from_checking_to_savings_is_invalid_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void four_hundred_dollar_transfer_from_checking_to_savings_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 400");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void over_four_hundred_dollar_transfer_from_checking_is_invalid_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 401");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void transferring_twice_between_savings_account_due_to_limit_is_invalid_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 400");
		assertTrue(commandValidator.validate(commandArgs));
		commandArgs = InputParser.parseCommand("transfer 12345678 87654321");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void zero_dollar_transfer_from_savings_to_checking_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 0");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void one_dollar_transfer_from_savings_to_checking_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void negative_amount_transfer_is_invalid_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 -1");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void thousand_dollar_transfer_to_checking_account_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1000");
		assertTrue(commandValidator.validate(commandArgs));
	}

	@Test
	void transfer_from_or_to_cd_account_is_invalid_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openCDAccount(ID_DIFFERENT, APR, 5000);
		String[] commandArgs = InputParser.parseCommand("transfer 87654321 12345677 3000");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void transfer_amount_with_unexpected_symbol_is_invalid_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1,000");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void transfer_amount_not_a_double_is_invalid_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 abc");
		assertFalse(commandValidator.validate(commandArgs));
	}

	@Test
	void transfer_amount_with_unexpected_characters_is_invalid_test() {
		bank.openSavingsAccount(ID, APR);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1aa");
		assertFalse(commandValidator.validate(commandArgs));
	}

}