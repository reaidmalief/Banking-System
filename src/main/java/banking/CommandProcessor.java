package banking;

class CommandProcessor {

	private final Bank bank;

	CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void execute(String[] commandArguments) {
		switch (commandArguments[0].toLowerCase()) {
		case "create":
			CreateCommandProcessor createProcessor = new CreateCommandProcessor(bank);
			createProcessor.execute(commandArguments);
			break;
		case "deposit":
			DepositCommandProcessor depositProcessor = new DepositCommandProcessor(bank);
			depositProcessor.execute(commandArguments);
			break;
		case "withdraw":
			WithdrawCommandProcessor withdrawProcessor = new WithdrawCommandProcessor(bank);
			withdrawProcessor.execute(commandArguments);
			break;
		case "pass":
			PassTimeCommandProcessor passTimeProcessor = new PassTimeCommandProcessor(bank);
			passTimeProcessor.execute(commandArguments);
			break;
		case "transfer":
			TransferCommandProcessor transferProcessor = new TransferCommandProcessor(bank);
			transferProcessor.execute(commandArguments);
			break;
		default:
			System.out.println("Unsupported operation: " + commandArguments[0]);
			break;
		}
	}
}
