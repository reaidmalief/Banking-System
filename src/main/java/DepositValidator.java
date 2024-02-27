class DepositValidator {

	private final Bank bankingSystem;

	DepositValidator(Bank bankingSystem) {
		this.bankingSystem = bankingSystem;
	}

	static boolean checkAccountExists(String[] args, Bank bankingSystem) {
		try {
			return bankingSystem.getAccounts().containsKey(args[1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	static boolean isCheckingAccountReference(String[] args, Bank bankingSystem) {
		try {
			Account acc = bankingSystem.getAccounts().get(args[1]);
			return acc instanceof CheckingAccount;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	static boolean isSavingsAccountReference(String[] args, Bank bankingSystem) {
		try {
			Account acc = bankingSystem.getAccounts().get(args[1]);
			return acc instanceof SavingsAccount;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	static boolean isCDAccountReference(String[] args, Bank bankingSystem) {
		try {
			Account acc = bankingSystem.getAccounts().get(args[1]);
			return acc instanceof CDAccount;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	static double parseToDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	boolean validate(String[] args) {
		if (checkAccountExists(args, bankingSystem)) {
			if (isCheckingAccountReference(args, bankingSystem)) {
				return checkCheckingAccountDepositValidity(args);
			} else if (isSavingsAccountReference(args, bankingSystem)) {
				return checkSavingsAccountDepositValidity(args);
			} else if (isCDAccountReference(args, bankingSystem)) {
				return false; // CD Account deposits have specific restrictions.
			}
		}
		return false;
	}

	private boolean checkCheckingAccountDepositValidity(String[] args) {
		double depositAmount = parseToDouble(args[2]);
		return (depositAmount >= 0 && depositAmount <= 1000);
	}

	private boolean checkSavingsAccountDepositValidity(String[] args) {
		double depositAmount = parseToDouble(args[2]);
		return (depositAmount >= 0 && depositAmount <= 2500);
	}
}
