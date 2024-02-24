import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountValidatorTest {

	private ValidationStrategy validator;

	@BeforeEach
	public void setUp() {
		validator = new ValidationStrategy();
	}

	@Test
	public void valid_checking_account_creation_command_test() {
		String command = "create checking 12345678 0.5";
		assertTrue(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_missing_apr_test() {
		String command = "create checking 12345678";
		assertFalse(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_incorrect_account_number_length() {
		String command = "create checking 1234567 0.5";
		assertFalse(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_negative_apr_test() {
		String command = "create checking 12345678 -0.5";
		assertFalse(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_zero_apr_test() {
		String command = "create checking 12345678 0";
		assertFalse(validator.validate(command));
	}

	@Test
	public void valid_savings_account_creation_command_test() {
		String command = "create savings 12345678 1.5";
		assertTrue(validator.validate(command));
	}

	@Test
	public void invalid_savings_account_creation_command_invalid_apr_test() {
		String command = "create savings 12345678 -1.0";
		assertFalse(validator.validate(command));
	}

	@Test
	public void valid_CD_account_creation_command_test() {
		String command = "create CD 12345678 1000 1.5";
		assertTrue(validator.validate(command));
	}

	@Test
	public void invalid_cd_account_creation_command_minimum_deposit_not_met_test() {
		String command = "create cd 12345678 500 1.5";
		assertFalse(validator.validate(command));
	}

	@Test
	public void valid_deposit_checking_account_test() {
		String command = "deposit checking 12345678 100";
		assertTrue(validator.validate(command));
	}

	@Test
	public void invalid_deposit_command_negative_amount_test() {
		String command = "deposit checking 12345678 -100";
		assertFalse(validator.validate(command));
	}

	@Test
	public void invalid_deposit_command_incorrect_account_type_test() {
		String command = "deposit gold 12345678 100"; // 'gold' is not a valid account type
		assertFalse(validator.validate(command));
	}

}
