package banking;

import static banking.DepositCommandValidator.doesAccountExist;
import static banking.DepositCommandValidator.isReferencingCDAccount;

public class TransferCommandValidator {

	private final Bank bank;
	private final CommandValidator validator;

	TransferCommandValidator(Bank bank) {
		this.bank = bank;
		this.validator = new CommandValidator(bank);
	}

	boolean validate(String[] commandArguments) {
		// Check if the expected number of arguments are provided to avoid
		// ArrayIndexOutOfBoundsException.
		if (commandArguments.length < 4) {
			return false;
		}

		if (doesAccountExist(commandArguments, bank, 1) && doesAccountExist(commandArguments, bank, 2)) {
			if (commandArguments[1].equals(commandArguments[2])) {
				return false;
			}

			if (isReferencingCDAccount(commandArguments, bank, 1)
					|| isReferencingCDAccount(commandArguments, bank, 2)) {
				return false;
			}

			return verifyDepositCriteriaForTransfer(commandArguments)
					&& verifyWithdrawalCriteriaForTransfer(commandArguments);
		}
		return false;
	}

	private boolean verifyDepositCriteriaForTransfer(String[] commandArguments) {
		String accountIDToDepositTo = commandArguments[2];
		String amountToTransfer = commandArguments[3];
		return validator.validate(new String[] { "deposit", accountIDToDepositTo, amountToTransfer });
	}

	private boolean verifyWithdrawalCriteriaForTransfer(String[] commandArguments) {
		String accountIDToWithdrawFrom = commandArguments[1];
		String amountToTransfer = commandArguments[3];
		return validator.validate(new String[] { "withdraw", accountIDToWithdrawFrom, amountToTransfer });
	}
}
