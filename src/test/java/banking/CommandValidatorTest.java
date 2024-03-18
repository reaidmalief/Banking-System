package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	private CommandValidator commandValidator;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void validate_create_command_test() {
		String[] commandArr = InputParser.parseCommand("create checking 12345678 0.1");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_create_command_in_all_caps_test() {
		String[] commandArr = InputParser.parseCommand("CREATE checking 12345678 0.1");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_create_command_with_typo_test() {
		String[] commandArr = InputParser.parseCommand("creat checking 12345678 0.1");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_create_command_with_unexpected_numbers_test() {
		String[] commandArr = InputParser.parseCommand("crea3te checking 12345678 0.1");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_deposit_command_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArr = InputParser.parseCommand("deposit 12345678 250");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_deposit_command_in_all_caps_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArr = InputParser.parseCommand("DEPOSIT 12345678 500");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_deposit_command_with_typo_test() {
		String[] commandArr = InputParser.parseCommand("depsoit 12345678 500");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_deposit_command_with_unexpected_number_test() {
		String[] commandArr = InputParser.parseCommand("deposit3 12345678 500");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_withdraw_command_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArr = InputParser.parseCommand("withdraw 12345678 400");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_withdraw_command_in_all_caps_test() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArr = InputParser.parseCommand("WITHDRAW 12345678 400");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_withdraw_command_with_typo_test() {
		String[] commandArr = InputParser.parseCommand("withdraer 12345678 500");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_withdraw_command_with_unexpected_numbers_test() {
		String[] commandArr = InputParser.parseCommand("withdra22w 12345678 500");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_transfer_command_test() {
		bank.openCheckingAccount("12345678", 0.1);
		bank.doDeposit("12345678", 500);
		bank.openCheckingAccount("87654321", 0.1);
		String[] commandArr = InputParser.parseCommand("transfer 12345678 87654321 300");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_transfer_command_in_all_caps_test() {
		bank.openCheckingAccount("12345678", 0.1);
		bank.doDeposit("12345678", 500);
		bank.openSavingsAccount("12345677", 0.1);
		String[] commandArr = InputParser.parseCommand("TRANSFER 12345678 12345677 100");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_transfer_command_with_typo_test() {
		bank.openCheckingAccount("12345678", 0.1);
		bank.doDeposit("12345678", 500);
		bank.openSavingsAccount("12345677", 0.1);
		String[] commandArr = InputParser.parseCommand("tansfer 12345678 12345677 300");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_transfer_command_with_unexpected_numbers_test() {
		bank.openCheckingAccount("12345678", 0.1);
		bank.doDeposit("12345678", 500);
		bank.openSavingsAccount("12345677", 0.1);
		String[] commandArr = InputParser.parseCommand("tr4n5f3r 12345678 12345674 300");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_pass_time_command_test() {
		String[] commandArr = InputParser.parseCommand("pass 1");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_pass_time_command_in_all_caps_test() {
		String[] commandArr = InputParser.parseCommand("PASS 1");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void validate_pass_time_command_with_typo_test() {
		String[] commandArr = InputParser.parseCommand("pas 1");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_pass_time_command_with_unexpected_numbers_test() {
		String[] commandArr = InputParser.parseCommand("pas3s 1");
		assertFalse(commandValidator.validate(commandArr));
	}

}
