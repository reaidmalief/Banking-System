package banking;

public class DepositCommandValidator {
	private final Bank bankingSystem;

	DepositCommandValidator(Bank bankingSystem) {
		this.bankingSystem = bankingSystem;
	}

	static double parseToDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	static boolean isReferencingCheckingAccount(String[] commandArguments, Bank bank) {
		try {
			Account account = bank.getAccounts().get(commandArguments[1]);
			return account instanceof CheckingAccount;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	static boolean isReferencingCDAccount(String[] commandArguments, Bank bank, int accountIDIndex) {
		try {
			Account account = bank.getAccounts().get(commandArguments[accountIDIndex]);
			return account instanceof CDAccount;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	static boolean isReferencingSavingsAccount(String[] commandArguments, Bank bank) {
		try {
			Account account = bank.getAccounts().get(commandArguments[1]);
			return account instanceof SavingsAccount;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	static boolean doesAccountExist(String[] commandArguments, Bank bank, int accountIDIndex) {
		try {
			return bank.getAccounts().containsKey(commandArguments[accountIDIndex]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean depositAmountIntoCheckingAccountIsValid(String[] commandArguments) {
		double depositAmount = parseToDouble(commandArguments[2]);
		return (depositAmount >= 0 && depositAmount <= 1000);
	}

	private boolean depositAmountIntoSavingsAccountIsValid(String[] commandArguments) {
		double depositAmount = parseToDouble(commandArguments[2]);
		return (depositAmount >= 0 && depositAmount <= 2500);
	}

	public boolean validate(String[] args) {
		if (args.length < 3) {
			return false;
		}

		if (!doesAccountExist(args, bankingSystem, 1)) {
			return false;
		}

		if (isReferencingCheckingAccount(args, bankingSystem)) {
			return depositAmountIntoCheckingAccountIsValid(args);
		} else if (isReferencingSavingsAccount(args, bankingSystem)) {
			return depositAmountIntoSavingsAccountIsValid(args);
		} else if (isReferencingCDAccount(args, bankingSystem, 1)) {

			return false;
		}

		return false;
	}

}
