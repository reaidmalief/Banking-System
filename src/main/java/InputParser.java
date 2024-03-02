public class InputParser {
	public static String[] parsedCommand(String commandAsStr) {
		return commandAsStr.stripTrailing().split(" ");
	}
}
