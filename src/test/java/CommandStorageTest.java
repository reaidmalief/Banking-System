import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	private CommandStorage commandStorage;

	@BeforeEach
	void setUp() {
		commandStorage = new CommandStorage();
	}

	@Test
	void testAddInvalidCommand() {
		commandStorage.addInvalidCommand("invalid command 1");
		commandStorage.addInvalidCommand("invalid command 2");

		List<String> invalidCommands = commandStorage.getAllInvalidCommands();
		assertEquals(2, invalidCommands.size());
		assertTrue(invalidCommands.contains("invalid command 1"));
		assertTrue(invalidCommands.contains("invalid command 2"));
	}

	@Test
	void testGetAllInvalidCommands() {
		List<String> invalidCommands = commandStorage.getAllInvalidCommands();
		assertNotNull(invalidCommands);
		assertTrue(invalidCommands.isEmpty());
	}
}
