package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {

	Account account;

	@BeforeEach
	public void setUp() {
		account = new CheckingAccount("12345678", 2.5);
	}

	@Test
	public void checking_account_initial_balance_test() {
		Account checking = new CheckingAccount("12345678", 1.5);
		assertEquals(0.0, checking.getBalance(), 0.01);
	}

	@Test
	public void savings_account_initial_balance_test() {
		Account savings = new SavingsAccount("87654321", 1.5);
		assertEquals(0.0, savings.getBalance(), 0.01);
	}

	@Test
	public void cd_account_initial_balance_test() {
		Account cd = new CDAccount("12348765", 3.0, 1000.0);
		assertEquals(1000, cd.getBalance(), 0.1);
	}

	@Test
	public void apr_assignment_test() {
		assertEquals(2.5, account.getApr(), 0.01);
	}

	@Test
	public void deposit_increases_balance_test() {
		account.deposit(200);
		assertEquals(200, account.getBalance(), 0.01);
	}

	@Test
	public void withdraw_decreases_balance_test() {
		account.deposit(200);
		account.withdraw(50);
		assertEquals(150, account.getBalance(), 0.01);
	}

	@Test
	public void balance_does_not_go_below_zero() {
		account.deposit(100);
		account.withdraw(150);
		assertEquals(0, account.getBalance(), 0.01);
	}

	@Test
	public void depositing_twice_increases_balance_correctly_test() {
		account.deposit(100);
		account.deposit(200);
		assertEquals(300, account.getBalance(), 0.01);
	}

	@Test
	public void withdrawing_twice_decreases_balance_correctly_test() {
		account.deposit(500);
		account.withdraw(100);
		account.withdraw(200);
		assertEquals(200, account.getBalance(), 0.1);
	}

}
