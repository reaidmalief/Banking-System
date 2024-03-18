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
			DepositCommandValidator validator = new DepositCommandValidator(bank);
			return validator.validate(commandArguments);
		} else if (commandArguments[0].equalsIgnoreCase("withdraw")) {
			WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);
			return validator.validate(commandArguments);
		} else if (commandArguments[0].equalsIgnoreCase("pass")) {
			PassTimeCommandValidator validator = new PassTimeCommandValidator();
			return validator.validate(commandArguments);
		} else if (commandArguments[0].equalsIgnoreCase("transfer")) {
			TransferCommandValidator validator = new TransferCommandValidator(bank);
			return validator.validate(commandArguments);
		} else {
			return false;
		}

	}
}
