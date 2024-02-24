public class CommandProcessor {
	private Bank bank;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void processCommand(String command) {
		String[] parts = command.split(" ");
		String commandType = parts[0];

		switch (commandType) {
		case "create":
			handleCreateCommand(parts);
			break;
		case "deposit":
			handleDepositCommand(parts);
			break;
		default:
			throw new IllegalArgumentException("Unsupported command");
		}
	}

	private void handleCreateCommand(String[] parts) {
		// Assuming parts[1] is the account type, parts[2] is the ID, and parts[3] is
		// the APR.
		String accountType = parts[1];
		String accountId = parts[2];
		double apr = Double.parseDouble(parts[3]);
		if ("cd".equals(accountType.toLowerCase()) && parts.length == 5) {
			double initialBalance = Double.parseDouble(parts[4]);
			bank.addAccount(accountType, accountId, apr, initialBalance);
		} else {
			Account account;
			switch (accountType.toLowerCase()) {
			case "checking":
				account = new CheckingAccount(accountId, apr);
				break;
			case "savings":
				account = new SavingsAccount(accountId, apr);
				break;
			default:
				throw new IllegalArgumentException("Unsupported account type: " + accountType);
			}
			bank.addAccount(account);

		}
	}

	private void handleDepositCommand(String[] parts) {
		// Assuming parts[1] is the ID and parts[2] is the deposit amount.
		String accountId = parts[1];
		double amount = Double.parseDouble(parts[2]);
		bank.deposit(accountId, amount);
	}
}
