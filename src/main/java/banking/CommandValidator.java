package banking;

public class CommandValidator {
	private final Bank bank;

	CommandValidator(Bank bank) {
		this.bank = bank;
	}

	boolean validate(String[] commandArguments) {
		if (commandArguments.length == 0) {
			return false;
		}
		switch (commandArguments[0].toLowerCase()) {
		case "create":
			return validateCreate(commandArguments);
		case "deposit":
			return validateDeposit(commandArguments);
		case "withdraw":
			return validateWithdraw(commandArguments);
		case "pass":
			return validatePass(commandArguments);
		case "transfer":
			return validateTransfer(commandArguments);
		default:
			return false;
		}
	}

	private boolean validateCreate(String[] commandArguments) {
		AccountCreationCommandValidator validator = new AccountCreationCommandValidator(bank);
		return validator.validate(commandArguments);
	}

	private boolean validateDeposit(String[] commandArguments) {
		DepositCommandValidator validator = new DepositCommandValidator(bank);
		return validator.validate(commandArguments);
	}

	private boolean validateWithdraw(String[] commandArguments) {
		WithdrawCommandValidator validator = new WithdrawCommandValidator(bank);
		return validator.validate(commandArguments);
	}

	private boolean validatePass(String[] commandArguments) {
		PassTimeCommandValidator validator = new PassTimeCommandValidator();
		return validator.validate(commandArguments);
	}

	private boolean validateTransfer(String[] commandArguments) {
		TransferCommandValidator validator = new TransferCommandValidator(bank);
		return validator.validate(commandArguments);
	}
}
