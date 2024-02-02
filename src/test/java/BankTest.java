import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {
	Bank bank;
	Account account1;
	Account account2;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		account1 = new CheckingAccount("12345678", 1.0);
		account2 = new CheckingAccount("87654321", 2.5);

	}

	@Test
	public void bank_has_no_account_initially_test() {
		assertEquals(0, bank.getNumOfAccounts());
	}

	@Test
	public void bank_has_one_account_after_adding_one() {
		bank.addAccount(account1);
		assertEquals(1, bank.getNumOfAccounts());
	}

	@Test
	public void bank_has_two_account_after_adding_two() {
		bank.addAccount(account1);
		bank.addAccount(account2);
		assertEquals(2, bank.getNumOfAccounts());
	}

	@Test
	public void retrieve_account_test() {
		bank.addAccount(account1);
		Account retrievedAccount = bank.getAccount("12345678");
		assertEquals(account1.getId(), retrievedAccount.getId());
	}

	@Test
	public void deposit_money_by_id_test() {
		bank.addAccount(account1);
		bank.deposit("12345678", 100.0);
		assertEquals(100.0, account1.getBalance());
	}

	@Test
	public void withdraw_money_by_id_test() {
		bank.addAccount(account1);
		bank.deposit("12345678", 100.0);
		bank.withdraw("12345678", 50.0);
		assertEquals(50.0, account1.getBalance());
	}

	@Test
	public void deposit_twice_by_id_test() {
		bank.addAccount(account1);
		bank.deposit("12345678", 100.0);
		bank.deposit("12345678", 200.0);
		assertEquals(300.0, account1.getBalance());
	}

	@Test
	public void withdraw_twice_by_id_test() {
		bank.addAccount(account1);
		bank.deposit("12345678", 300.0);
		bank.withdraw("12345678", 100.0);
		bank.withdraw("12345678", 50.0);
		assertEquals(150.0, account1.getBalance());
	}

}
