package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandStorage {

	public final Bank bank;
	private final List<String> invalidCommands = new ArrayList<>();
	private final Map<String, List<String>> validCommands = new HashMap<>();

	public CommandStorage(Bank bank) {
		this.bank = bank;
	}

	public void storeInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getInvalidCommands() {
		return invalidCommands;
	}

	public Map<String, List<String>> getValidCommands() {
		return validCommands;
	}

	public void storeValidCommand(String command) {
		InputParser input = new InputParser();
		String[] commandArguments = input.parseCommand(command);

		if (commandArguments[0].equalsIgnoreCase("create") || commandArguments[0].equalsIgnoreCase("withdraw")
				|| commandArguments[0].equalsIgnoreCase("deposit")) {
			insertIntoMap(validCommands, commandArguments[1], command);
		} else if (commandArguments[0].equalsIgnoreCase("transfer")) {
			insertIntoMap(validCommands, commandArguments[1], command);
			insertIntoMap(validCommands, commandArguments[2], command);
		}
	}

	private void insertIntoMap(Map<String, List<String>> map, String accountID, String command) {
		if (map.get(accountID) != null) {
			map.get(accountID).add(command);
		} else if (map.get(accountID) == null) {
			map.put(accountID, new ArrayList<>());
			map.get(accountID).add(command);
		}
	}
}
