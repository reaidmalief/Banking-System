package banking;

class CommandProcessor {

	private final Bank bank;

	CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public void execute(String[] commandArguments) {
		if (commandArguments == null || commandArguments.length == 0) {
			System.out.println("No command provided.");
			return;
		}

		switch (commandArguments[0].toLowerCase()) {
		case "create":
			executeCreate(commandArguments);
			break;
		case "deposit":
			executeDeposit(commandArguments);
			break;
		case "withdraw":
			executeWithdraw(commandArguments);
			break;
		case "pass":
			executePass(commandArguments);
			break;
		case "transfer":
			executeTransfer(commandArguments);
			break;
		default:
			System.out.println("Unsupported operation: " + commandArguments[0]);
			break;
		}
	}

	private void executeCreate(String[] commandArguments) {
		CreateCommandProcessor createProcessor = new CreateCommandProcessor(bank);
		createProcessor.execute(commandArguments);
	}

	private void executeDeposit(String[] commandArguments) {
		DepositCommandProcessor depositProcessor = new DepositCommandProcessor(bank);
		depositProcessor.execute(commandArguments);
	}

	private void executeWithdraw(String[] commandArguments) {
		WithdrawCommandProcessor withdrawProcessor = new WithdrawCommandProcessor(bank);
		withdrawProcessor.execute(commandArguments);
	}

	private void executePass(String[] commandArguments) {
		PassTimeCommandProcessor passTimeProcessor = new PassTimeCommandProcessor(bank);
		passTimeProcessor.execute(commandArguments);
	}

	private void executeTransfer(String[] commandArguments) {
		TransferCommandProcessor transferProcessor = new TransferCommandProcessor(bank);
		transferProcessor.execute(commandArguments);
	}
}
