import java.util.ArrayList;
import java.util.List;

public class CommandStorage {
	private final List<String> invalidCommands;

	public CommandStorage() {
		this.invalidCommands = new ArrayList<>();
	}

	public void addInvalidCommand(String command) {
		invalidCommands.add(command);
	}

	public List<String> getAllInvalidCommands() {
		return new ArrayList<>(invalidCommands);
	}
}
