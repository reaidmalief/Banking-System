package banking;

public class AccountCreationCommandValidator {
	private final Bank bank;

	AccountCreationCommandValidator(Bank bank) {
		this.bank = bank;
	}

	boolean validate(String[] commandArguments) {
		if (isCreatingCheckingAccount(commandArguments) || isCreatingSavingsAccount(commandArguments)) {
			return validateCheckingOrSavingsAccountArguments(commandArguments);
		} else if (isCreatingCDAccount(commandArguments)) {
			return validateCDAccountArguments(commandArguments);
		}
		return false;
	}

	private boolean validateCDAccountArguments(String[] commandArguments) {
		try {
			return creatingCDAccountHasProperNumberOfArguments(commandArguments)
					&& isAValidAccountID(commandArguments[2]) && validateThatAccountIDDoesNotExist(commandArguments[2])
					&& isValidAPR(commandArguments[3]) && isWithinCDStartingBalanceLimits(commandArguments[4]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean validateThatAccountIDDoesNotExist(String accountID) {
		return !bank.getAccounts().containsKey(accountID);
	}

	private boolean validateCheckingOrSavingsAccountArguments(String[] commandArguments) {
		try {
			return creatingCheckingOrSavingsHasProperNumberOfArguments(commandArguments)
					&& validateThatAccountIDDoesNotExist(commandArguments[2])
					&& startsWithBalanceOfZero(commandArguments) && isAValidAccountID(commandArguments[2])
					&& isValidAPR(commandArguments[3]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean creatingCDAccountHasProperNumberOfArguments(String[] commandArguments) {
		return commandArguments.length == 5;
	}

	private boolean creatingCheckingOrSavingsHasProperNumberOfArguments(String[] commandArguments) {
		return commandArguments.length == 4;
	}

	private boolean isAValidAccountID(String accountID) {
		return isInteger(accountID) && accountID.length() == 8;
	}

	private boolean isValidAPR(String APR) {
		double parsedAPR;
		try {
			parsedAPR = Double.parseDouble(APR);
		} catch (NumberFormatException e) {
			return false;
		}
		return (parsedAPR >= 0 && parsedAPR <= 10);
	}

	private boolean isCreatingCheckingAccount(String[] commandArguments) {
		return commandArguments[1].equalsIgnoreCase("checking");
	}

	private boolean isCreatingSavingsAccount(String[] commandArguments) {
		return commandArguments[1].equalsIgnoreCase("savings");
	}

	private boolean isCreatingCDAccount(String[] commandArguments) {
		return commandArguments[1].equalsIgnoreCase("cd");
	}

	private boolean startsWithBalanceOfZero(String[] commandArguments) {
		return commandArguments.length != 5; // This logic seems off for determining balance of zero. Review needed.
	}

	private boolean isWithinCDStartingBalanceLimits(String startingBalanceAsString) {
		double startingBalance;
		try {
			startingBalance = Double.parseDouble(startingBalanceAsString);
		} catch (NumberFormatException e) {
			return false;
		}
		return (startingBalance >= 1000 && startingBalance <= 10000);
	}

	private boolean isInteger(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
