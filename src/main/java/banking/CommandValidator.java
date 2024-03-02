package banking;

public class CommandValidator {
	private final Bank bank;

	CommandValidator(Bank bank) {
		this.bank = bank;
	}

	boolean validate(String[] commandArguments) {
		if (commandArguments[0].equalsIgnoreCase("create")) {
			AccountCreationCommandValidator validator = new AccountCreationCommandValidator(bank);
			return validator.validate(commandArguments);
		} else if (commandArguments[0].equalsIgnoreCase("deposit")) {
			DepositValidator validator = new DepositValidator(bank);
			return validator.validate(commandArguments);
		} else {
			return false;
		}
	}
}
