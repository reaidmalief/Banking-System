import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AccountValidatorTest {
	@Test
	public void valid_checking_account_creation_command_test() {
		String command = "create checking 12345678 0.5";
		AccountValidator validator = new AccountValidator();
		assertTrue(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_missing_apr_test() {
		String command = "create checking 12345678";
		AccountValidator validator = new AccountValidator();
		assertFalse(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_incorrect_account_number_length() {
		String command = "create checking 1234567 0.5";
		AccountValidator validator = new AccountValidator();
		assertFalse(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_negative_apr_test() {
		String command = "create checking 12345678 -0.5";
		AccountValidator validator = new AccountValidator();
		assertFalse(validator.validate(command));
	}

	@Test
	public void invalid_checking_account_creation_command_zero_apr_test() {
		String command = "create checking 12345678 0"; // APR is zero
		AccountValidator validator = new AccountValidator();
		assertFalse(validator.validate(command));
	}

	@Test
	public void valid_savings_account_creation_command_test() {
		String command = "create savings 12345678 1.5";
		AccountValidator validator = new AccountValidator();
		assertTrue(validator.validate(command));
	}

	@Test
	public void invalid_savings_account_creation_command_invalid_apr_test() {
		String command = "create savings 12345678 -1.0";
		AccountValidator validator = new AccountValidator();
		assertFalse(validator.validate(command));
	}

	@Test
	public void valid_CD_account_creation_command_test() {
		String command = "create CD 12345678 1000 1.5";
		AccountValidator validator = new AccountValidator();
		assertTrue(validator.validate(command));
	}

	@Test
	public void invalid_cd_account_creation_command_minimum_deposit_not_met_test() {
		String command = "create cd 12345678 500 1.5";
		AccountValidator validator = new AccountValidator();
		assertFalse(validator.validate(command));
	}

}
