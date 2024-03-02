package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private CommandProcessor processor;
	private Bank bank;
	private InputParser inputParser;

	@BeforeEach
	void setup() {
		bank = new Bank();
		processor = new CommandProcessor(bank);
		inputParser = new InputParser();
	}

	@Test
	void open_checking_account() {
		String[] commandArgs = inputParser.parseCommand("create checking 12345678 0.01");
		processor.execute(commandArgs);
		assertEquals("12345678", bank.getAccounts().get("12345678").getId());
		assertEquals(0.01, bank.getAccounts().get("12345678").getApr(), 0.001);
	}

	@Test
	void deposit_into_empty_checking_account() {
		bank.openCheckingAccount("12345678", 0.01);
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 1000");
		processor.execute(commandArgs);
		assertEquals(1000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}

	@Test
	void deposit_into_checking_with_balance_greater_than_zero() {
		bank.openCheckingAccount("12345678", 0.01);
		bank.deposit("12345678", 1000);
		double initialBalance = bank.getAccounts().get("12345678").getBalance();
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 1000");
		processor.execute(commandArgs);
		assertEquals(initialBalance + 1000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}

	@Test
	void deposit_into_empty_savings_account() {
		bank.openSavingsAccount("12345677");
		String[] commandArgs = inputParser.parseCommand("deposit 12345677 1000");
		processor.execute(commandArgs);
		assertEquals(1000, bank.getAccounts().get("12345677").getBalance(), 0.001);
	}

	@Test
	void deposit_into_savings_with_balance_greater_than_zero() {
		bank.openSavingsAccount("12345678");
		bank.deposit("12345678", 1000);
		double initialBalance = bank.getAccounts().get("12345678").getBalance();
		String[] commandArgs = inputParser.parseCommand("deposit 12345678 1000");
		processor.execute(commandArgs);
		assertEquals(initialBalance + 1000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}

	@Test
	void deposit_into_checking_account_twice() {
		bank.openCheckingAccount("12345678", 0.01);
		String[] firstCommandArgs = inputParser.parseCommand("deposit 12345678 1000");
		processor.execute(firstCommandArgs);
		String[] secondCommandArgs = inputParser.parseCommand("deposit 12345678 1000");
		processor.execute(secondCommandArgs);
		assertEquals(2000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}
}