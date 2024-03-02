package banking;

public class CreateCommandProcessor {
	private final Bank bank;

	CreateCommandProcessor(Bank bank) {
		this.bank = bank;
	}

	void execute(String[] commandArguments) {
		if (commandArguments[1].equalsIgnoreCase("checking")) {
			double apr = Double.parseDouble(commandArguments[3]);
			bank.openCheckingAccount(commandArguments[2], apr);

		}
	}
}
