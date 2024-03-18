package banking;

import static banking.BankTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	private CommandProcessor processor;
	private Bank bank;

	@BeforeEach
	void setup() {
		bank = new Bank();
		processor = new CommandProcessor(bank);
	}

	@Test
	void open_checking_account_test() {
		String[] commandArgs = InputParser.parseCommand("create checking 12345678 0.01");
		processor.execute(commandArgs);
		assertEquals("12345678", bank.getAccounts().get("12345678").getId());
		assertEquals(0.01, bank.getAccounts().get("12345678").getApr(), 0.001);
	}

	@Test
	void deposit_into_empty_checking_account_test() {
		bank.openCheckingAccount("12345678", 0.01);
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1000");
		processor.execute(commandArgs);
		assertEquals(1000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}

	@Test
	void deposit_into_checking_with_balance_greater_than_zero_test() {
		bank.openCheckingAccount("12345678", 0.01);
		bank.doDeposit("12345678", 1000);
		double initialBalance = bank.getAccounts().get("12345678").getBalance();
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1000");
		processor.execute(commandArgs);
		assertEquals(initialBalance + 1000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}

	@Test
	void deposit_into_empty_savings_account_test() {
		bank.openSavingsAccount("12345677", 0.1);
		String[] commandArgs = InputParser.parseCommand("deposit 12345677 1000");
		processor.execute(commandArgs);
		assertEquals(1000, bank.getAccounts().get("12345677").getBalance(), 0.001);
	}

	@Test
	void deposit_into_savings_with_balance_greater_than_zero_test() {
		bank.openSavingsAccount("12345678", 0.1);
		bank.doDeposit("12345678", 1000);
		double initialBalance = bank.getAccounts().get("12345678").getBalance();
		String[] commandArgs = InputParser.parseCommand("deposit 12345678 1000");
		processor.execute(commandArgs);
		assertEquals(initialBalance + 1000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}

	@Test
	void deposit_into_checking_account_twice_test() {
		bank.openCheckingAccount("12345678", 0.01);
		String[] firstCommandArgs = InputParser.parseCommand("deposit 12345678 1000");
		processor.execute(firstCommandArgs);
		String[] secondCommandArgs = InputParser.parseCommand("deposit 12345678 1000");
		processor.execute(secondCommandArgs);
		assertEquals(2000, bank.getAccounts().get("12345678").getBalance(), 0.001);
	}

	@Test
	void withdraw_from_a_savings_account_test() {
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		bank.doDeposit(ID_DIFFERENT, 100);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345677 50");
		processor.execute(commandArgs);
		assertEquals(100 - 50, bank.getAccounts().get(ID_DIFFERENT).getBalance());
	}

	@Test
	void withdraw_from_checking_account_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, 1000);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 500");
		processor.execute(commandArgs);
		assertEquals(1000 - 500, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void withdraw_from_a_cd_account_test() {
		bank.openCDAccount(ID, APR, 2500);
		String[] commandArgs = InputParser.parseCommand("withdraw 12345678 2500");
		processor.execute(commandArgs);
		assertEquals(0, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void pass_time_one_month_when_balance_is_zero_test() {
		bank.openCheckingAccount(ID, 1.0);
		String[] commandArgs = InputParser.parseCommand("pass 1");
		processor.execute(commandArgs);
		assertNull(bank.getAccounts().get(ID));
	}

	@Test
	void pass_time_one_month_when_balance_is_greater_than_zero_and_less_than_100_test() {
		bank.openCheckingAccount(ID, 1.0);
		bank.doDeposit(ID, 55);
		String[] commandArgs = InputParser.parseCommand("pass 1");
		processor.execute(commandArgs);
		assertEquals(55.05, Math.round(bank.getAccounts().get(ID).getBalance() * 100.0) / 100.0);
	}

	@Test
	void pass_time_one_month_when_balance_is_greater_than_100_test() {
		bank.openCheckingAccount(ID, 1.0);
		bank.doDeposit(ID, 5000);
		String[] commandArgs = InputParser.parseCommand("pass 1");
		processor.execute(commandArgs);
		assertEquals(5004.17, Math.round(bank.getAccounts().get(ID).getBalance() * 100.0) / 100.0);
	}

	@Test
	void pass_time_one_month_when_cd_account_present_test() {
		bank.openCDAccount(ID, 1.0, 5000);
		String[] commandArgs = InputParser.parseCommand("pass 1");
		processor.execute(commandArgs);
		assertEquals(5016.69, Math.round(bank.getAccounts().get(ID).getBalance() * 100.0) / 100.0);
	}

	@Test
	void pass_time_twice_test() {
		bank.openCheckingAccount(ID, 1.0);
		bank.doDeposit(ID, 125);
		String[] commandArgs = InputParser.parseCommand("pass 3");
		processor.execute(commandArgs);
		assertEquals(125.31, Math.round(bank.getAccounts().get(ID).getBalance() * 100.0) / 100.0);
		commandArgs = InputParser.parseCommand("pass 2");
		processor.execute(commandArgs);
		assertEquals(125.52, Math.round(bank.getAccounts().get(ID).getBalance() * 100.0) / 100.0);
	}

	@Test
	void transfer_between_two_checking_accounts_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, 1000);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 200");
		processor.execute(commandArgs);
		assertEquals(800, bank.getAccounts().get(ID).getBalance());
		assertEquals(200, bank.getAccounts().get(ID_DIFFERENT).getBalance());
	}

	@Test
	void transfer_between_two_savings_accounts_test() {
		bank.openSavingsAccount(ID, APR);
		bank.doDeposit(ID, 2500);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 500");
		processor.execute(commandArgs);
		assertEquals(2000, bank.getAccounts().get(ID).getBalance());
		assertEquals(500, bank.getAccounts().get(ID_DIFFERENT).getBalance());
	}

	@Test
	void transfer_from_checking_to_savings_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, 1000);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 400");
		processor.execute(commandArgs);
		assertEquals(600, bank.getAccounts().get(ID).getBalance());
		assertEquals(400, bank.getAccounts().get(ID_DIFFERENT).getBalance());
	}

	@Test
	void transfer_from_savings_to_checking_test() {
		bank.openSavingsAccount(ID, APR);
		bank.doDeposit(ID, 2500);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 1000");
		processor.execute(commandArgs);
		assertEquals(1500, bank.getAccounts().get(ID).getBalance());
		assertEquals(1000, bank.getAccounts().get(ID_DIFFERENT).getBalance());
	}

	@Test
	void transfer_twice_between_accounts_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, 1000);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		String[] commandArgs = InputParser.parseCommand("transfer 12345678 12345677 400");
		processor.execute(commandArgs);
		commandArgs = InputParser.parseCommand("transfer 12345678 12345677 400");
		processor.execute(commandArgs);
		assertEquals(200, bank.getAccounts().get(ID).getBalance());
		assertEquals(800, bank.getAccounts().get(ID_DIFFERENT).getBalance());
	}
}