package banking;

import java.util.List;

public class MasterControl {
	private final CommandValidator commandValidator;
	private final CommandProcessor commandProcessor;
	private final CommandStorage commandStorage;

	public MasterControl(CommandValidator commandValidator, CommandProcessor commandProcessor,
			CommandStorage commandStorage) {
		this.commandValidator = commandValidator;
		this.commandProcessor = commandProcessor;
		this.commandStorage = commandStorage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			String[] parsedCommand = InputParser.parseCommand(command);

			if (commandValidator.validate(parsedCommand)) {
				commandProcessor.execute(parsedCommand);
				commandStorage.storeValidCommand(command);
			} else {
				commandStorage.storeInvalidCommand(command);
			}
		}
		return commandStorage.getOutput();
	}
}
