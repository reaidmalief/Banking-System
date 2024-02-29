import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandValidatorTest {

	private CommandValidator commandValidator;
	private InputParser commandInput;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
		commandValidator = new CommandValidator(bank);
		commandInput = new InputParser();
	}

	@Test
	void validate_a_create_command() {
		String[] commandArr = commandInput.parseCommand("create checking 12345678 0.1");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void create_in_all_caps_is_a_valid_command() {
		String[] commandArr = commandInput.parseCommand("CREATE checking 12345678 0.1");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void typo_in_create_is_an_invalid_command() {
		String[] commandArr = commandInput.parseCommand("creat checking 12345678 0.1");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void create_has_unexpected_numbers_which_makes_command_invalid() {
		String[] commandArr = commandInput.parseCommand("crea3te checking 12345678 0.1");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void validate_a_deposit_command() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArr = commandInput.parseCommand("deposit 12345678 250");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void deposit_in_all_caps_is_valid() {
		bank.openCheckingAccount("12345678", 0.1);
		String[] commandArr = commandInput.parseCommand("DEPOSIT 12345678 500");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void typo_in_deposit_command_is_invalid() {
		String[] commandArr = commandInput.parseCommand("depsoit 12345678 500");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void unexpected_number_in_deposit_is_invalid() {
		String[] commandArr = commandInput.parseCommand("deposit3 12345678 500");
		assertFalse(commandValidator.validate(commandArr));
	}

}
