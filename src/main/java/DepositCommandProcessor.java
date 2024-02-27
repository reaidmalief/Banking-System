class DepositCommandProcessor {

	private final Bank bank;

	DepositCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void execute(String[] commandArguments) {
		String accountID = commandArguments[1];
		double amount = Double.parseDouble(commandArguments[2]);
		bank.getAccounts().get(accountID).deposit(amount);
	}
}