public class InputParser {
	public String[] parseCommand(String input) {
		return input.trim().split("\\s+");
	}
}
