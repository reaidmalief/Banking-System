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
		// Ensuring the validation logic is straightforward and avoids deep nesting:
		// 1. Checks if the required number of arguments are provided.
		// 2. Verifies both accounts exist.
		// 3. Ensures the accounts are not the same.
		// 4. Checks neither account is a CD account, which cannot participate in
		// transfers.
		// 5. Validates both deposit and withdrawal criteria for a transfer.
		return commandArguments.length >= 4 && doesAccountExist(commandArguments, bank, 1)
				&& doesAccountExist(commandArguments, bank, 2) && !commandArguments[1].equals(commandArguments[2])
				&& !isReferencingCDAccount(commandArguments, bank, 1)
				&& !isReferencingCDAccount(commandArguments, bank, 2)
				&& verifyDepositCriteriaForTransfer(commandArguments)
				&& verifyWithdrawalCriteriaForTransfer(commandArguments);
	}

	private boolean verifyDepositCriteriaForTransfer(String[] commandArguments) {
		// this method validates the deposit part of the transfer.
		return validator.validate(new String[] { "deposit", commandArguments[2], commandArguments[3] });
	}

	private boolean verifyWithdrawalCriteriaForTransfer(String[] commandArguments) {
		// this method validates the withdrawal part of the transfer.
		return validator.validate(new String[] { "withdraw", commandArguments[1], commandArguments[3] });
	}
}
