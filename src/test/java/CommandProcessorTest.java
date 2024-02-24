import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private Bank bank;
	private CommandProcessor processor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		processor = new CommandProcessor(bank);
	}

	@Test
	public void testCreateCheckingAccount() {
		String command = "create checking 12345678 1.0";
		processor.processCommand(command);
		Account result = bank.getAccount("12345678");
		assertNotNull(result);
		assertTrue(result instanceof CheckingAccount);
		assertEquals(1.0, result.getApr(), 0.001);
	}

	@Test
	public void testDepositIntoAccount() {
		// Create account first
		bank.addAccount(new CheckingAccount("12345678", 1.0));
		String command = "deposit 12345678 100";
		processor.processCommand(command);
		Account result = bank.getAccount("12345678");
		assertNotNull(result);
		assertEquals(100, result.getBalance(), 0.001);
	}
}
