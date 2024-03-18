package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassCommandValidatorTest {

	private CommandValidator commandValidator;

	@BeforeEach
	void setup() {
		Bank bank = new Bank();
		commandValidator = new CommandValidator(bank);
	}

	@Test
	void empty_command_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void missing_month_in_command_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("pass");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void month_less_than_one_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("pass 0");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void month_at_least_one_is_valid_test() {
		String[] commandArr = InputParser.parseCommand("pass 1");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void negative_month_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("pass -1");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void max_sixty_months_is_valid_test() {
		String[] commandArr = InputParser.parseCommand("pass 60");
		assertTrue(commandValidator.validate(commandArr));
	}

	@Test
	void more_than_sixty_months_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("pass 61");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void symbol_in_month_argument_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("pass 1,00");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void non_integer_month_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("pass kfd");
		assertFalse(commandValidator.validate(commandArr));
	}

	@Test
	void letters_in_month_is_invalid_test() {
		String[] commandArr = InputParser.parseCommand("pass 2d");
		assertFalse(commandValidator.validate(commandArr));
	}

}
