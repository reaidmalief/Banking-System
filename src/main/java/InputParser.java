public class InputParser {
	public static String[] parseCommand(String commandAsStr) {
		return commandAsStr.stripTrailing().split(" ");
	}
}
