package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommandStorageTest {
	private final String INVALID_CREATE_COMMAND = "creat savings 12345678 0.01";
	private final String INVALID_DEPOSIT_COMMAND = "deposit savings 124345678 0.01";
	public Bank bank;
	private CommandStorage commandStorage;

	@BeforeEach
	void setup() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	void invalid_commands_in_storage_is_empty_test() {
		assertEquals(0, commandStorage.getInvalidCommands().size());
	}

	@Test
	void store_invalid_command_test() {
		commandStorage.storeInvalidCommand(INVALID_CREATE_COMMAND);
		assertEquals(INVALID_CREATE_COMMAND, commandStorage.getInvalidCommands().get(0));
	}

	@Test
	void store_two_invalid_commands_test() {
		commandStorage.storeInvalidCommand(INVALID_CREATE_COMMAND);
		commandStorage.storeInvalidCommand(INVALID_DEPOSIT_COMMAND);
		assertEquals(INVALID_CREATE_COMMAND, commandStorage.getInvalidCommands().get(0));
		assertEquals(INVALID_DEPOSIT_COMMAND, commandStorage.getInvalidCommands().get(1));
	}

	@Test
	void only_one_invalid_command_is_currently_stored_test() {
		commandStorage.storeInvalidCommand(INVALID_CREATE_COMMAND);
		assertEquals(1, commandStorage.getInvalidCommands().size());
	}

	@Test
	void two_invalid_commands_currently_stored_test() {
		commandStorage.storeInvalidCommand(INVALID_CREATE_COMMAND);
		commandStorage.storeInvalidCommand(INVALID_DEPOSIT_COMMAND);
		assertEquals(2, commandStorage.getInvalidCommands().size());
	}

	@Test
	void initial_transaction_history_is_empty_test() {
		assertEquals(0, commandStorage.getValidCommands().size());
	}
}
