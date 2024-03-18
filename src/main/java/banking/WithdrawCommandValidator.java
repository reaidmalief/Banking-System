package banking;

import static banking.DepositCommandValidator.*;

class WithdrawCommandValidator {
	private final Bank bank;

	WithdrawCommandValidator(Bank bank) {
		this.bank = bank;
	}

	boolean validate(String[] commandArguments) {
		if (!doesAccountExist(commandArguments, bank, 1)) {
			return false;
		}

		if (isReferencingCheckingAccount(commandArguments, bank)) {
			return isCheckingWithdrawalAmountValid(commandArguments);
		}

		if (isReferencingSavingsAccount(commandArguments, bank)) {
			return isSavingsWithdrawalAmountValid(commandArguments)
					&& canWithdrawFromSavingsThisMonth(commandArguments);
		}

		if (isReferencingCDAccount(commandArguments, bank, 1)) {
			return isCDWithdrawalAmountValid(commandArguments) && isCDAccountMaturityReached(commandArguments);
		}

		return false;
	}

	private boolean isSavingsWithdrawalAmountValid(String[] commandArguments) {
		double withdrawAmount = parseToDouble(commandArguments[2]);
		return (withdrawAmount >= 0 && withdrawAmount <= 1000);
	}

	private boolean isCheckingWithdrawalAmountValid(String[] commandArguments) {
		double withdrawAmount = parseToDouble(commandArguments[2]);
		return (withdrawAmount >= 0 && withdrawAmount <= 400);
	}

	private boolean isCDWithdrawalAmountValid(String[] commandArguments) {
		double withdrawAmount = parseToDouble(commandArguments[2]);
		double currentCDAccountBalance = bank.getAccounts().get(commandArguments[1]).getBalance();
		return withdrawAmount >= currentCDAccountBalance;
	}

	private boolean isCDAccountMaturityReached(String[] commandArguments) {
		int accountAge = bank.getAccounts().get(commandArguments[1]).getMonths();
		return accountAge >= 12 && accountAge % 12 == 0;
	}

	private boolean canWithdrawFromSavingsThisMonth(String[] commandArguments) {
		SavingsAccount account = (SavingsAccount) bank.getAccounts().get(commandArguments[1]);
		return !account.getHasWithdrawnThisMonth();
	}
}