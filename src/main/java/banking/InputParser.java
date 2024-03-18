package banking;

public class InputParser {
	private InputParser() {

	}

	public static String[] parseCommand(String commandAsStr) {
		return commandAsStr.stripTrailing().split(" ");
	}
}
