package banking;

public class DepositValidator {

	private final Bank bankingSystem;

	DepositValidator(Bank bankingSystem) {
		this.bankingSystem = bankingSystem;
	}

	private static double parseToDouble(String value) {
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	// Use instance methods instead of static methods when accessing bankingSystem
	private boolean checkAccountExists(String accountId) {
		try {
			return bankingSystem.getAccounts().containsKey(accountId);
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean isAccountTypeReference(String accountId, Class<? extends Account> accountType) {
		Account acc = bankingSystem.getAccounts().get(accountId);
		return accountType.isInstance(acc);
	}

	public boolean validate(String[] args) {
		if (args.length < 3) {
            return false; // Ensure there are enough arguments
        }

		String accountId = args[1];
		if (!checkAccountExists(accountId)) {
            return false;
        }

		// Refactored validation checks using streamlined account type checks
		double depositAmount = parseToDouble(args[2]);
		if (isAccountTypeReference(accountId, CheckingAccount.class)) {
			return depositAmount >= 0 && depositAmount <= 1000;
		} else if (isAccountTypeReference(accountId, SavingsAccount.class)) {
			return depositAmount >= 0 && depositAmount <= 2500;
		} else if (isAccountTypeReference(accountId, CDAccount.class)) {
			// CD banking.Account deposits have specific restrictions.
			return false;
		}

		return false;
	}
}
