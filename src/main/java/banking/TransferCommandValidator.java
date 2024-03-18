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
		boolean isValid = true; // Start with assuming the command is valid

		// Check if the expected number of arguments are provided to avoid
		// ArrayIndexOutOfBoundsException.
		if (commandArguments.length < 4) {
			return false; // Immediate return here is necessary due to argument length check
		}

		// Both accounts must exist and not be the same, neither can be a CD account.
		boolean bothAccountsExist = doesAccountExist(commandArguments, bank, 1)
				&& doesAccountExist(commandArguments, bank, 2);
		boolean accountsAreDifferent = !commandArguments[1].equals(commandArguments[2]);
		boolean notCDAccount = !isReferencingCDAccount(commandArguments, bank, 1)
				&& !isReferencingCDAccount(commandArguments, bank, 2);

		if (!bothAccountsExist || !accountsAreDifferent || !notCDAccount) {
			isValid = false; // Set isValid to false without returning immediately
		} else {
			// Only proceed with further checks if the initial conditions are met
			boolean depositCriteriaValid = verifyDepositCriteriaForTransfer(commandArguments);
			boolean withdrawalCriteriaValid = verifyWithdrawalCriteriaForTransfer(commandArguments);

			// If either deposit or withdrawal criteria are not met, the command is invalid
			if (!depositCriteriaValid || !withdrawalCriteriaValid) {
				isValid = false; // Update isValid based on criteria checks
			}
		}

		return isValid; // Return the result of the validations
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
