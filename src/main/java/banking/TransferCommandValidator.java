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
		// Early return if the number of arguments is less than expected.
		if (commandArguments.length < 4) {
			return false;
		}

		// Check if both accounts exist. If either doesn't, return false early.
		if (!doesAccountExist(commandArguments, bank, 1) || !doesAccountExist(commandArguments, bank, 2)) {
			return false;
		}

		// Check if the accounts are the same or if either is a CD account, which is not
		// allowed.
		if (commandArguments[1].equals(commandArguments[2]) || isReferencingCDAccount(commandArguments, bank, 1)
				|| isReferencingCDAccount(commandArguments, bank, 2)) {
			return false;
		}

		// Finally, validate the transfer amount for both withdrawal and deposit
		// criteria.
		return verifyDepositCriteriaForTransfer(commandArguments)
				&& verifyWithdrawalCriteriaForTransfer(commandArguments);
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
