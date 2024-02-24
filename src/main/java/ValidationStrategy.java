import java.util.HashMap;
import java.util.Map;

public class ValidationStrategy {
	public Map<String, AccountCreationValidator> accountCreationValidators;
	public DepositValidator depositValidator;

	public ValidationStrategy() {
		accountCreationValidators = new HashMap<>();
		accountCreationValidators.put("checking", new CheckingAccountCreationValidator());
		accountCreationValidators.put("savings", new SavingsAccountCreationValidator());
		accountCreationValidators.put("cd", new CDAccountCreationValidator());
		depositValidator = new GenericDepositValidator();
	}

	public boolean validate(String command) {
		String[] parts = command.split(" ");
		if (parts.length < 3) {
			return false;
		}

		String operation = parts[0].toLowerCase();
		String accountType = parts[1].toLowerCase();

		switch (operation) {
		case "create":
			if (accountCreationValidators.containsKey(accountType)) {
				return accountCreationValidators.get(accountType).validate(command);
			}
			return false;
		case "deposit":
			return depositValidator.validate(command);
		default:
			return false;
		}
	}
}
