package banking;

public class WithdrawCommandProcessor {
	private final Bank bank;

	WithdrawCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void execute(String[] commandArguments) {
		String accountID = commandArguments[1];
		double amount = Double.parseDouble(commandArguments[2]);
		bank.doWithdraw(accountID, amount);

	}
}
