package banking;

import static banking.BankTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SavingsAccountTest {
	private SavingsAccount account;

	@BeforeEach
	void setUp() {
		account = new SavingsAccount(ID, APR);
	}

	@Test
	void initial_balance_is_zero_test() {
		assertEquals(0, account.getBalance());
	}

	@Test
	void deposit_increases_balance_test() {
		account.deposit(AMOUNT);
		assertEquals(AMOUNT, account.getBalance());
	}

	@Test
	void double_deposit_doubles_balance_test() {
		account.deposit(AMOUNT);
		account.deposit(AMOUNT);
		assertEquals(AMOUNT * 2, account.getBalance());
	}

	@Test
	void withdrawal_reduces_balance_test() {
		account.deposit(AMOUNT * 2);
		account.withdraw(AMOUNT);
		assertEquals(AMOUNT, account.getBalance());
	}

	@Test
	void double_withdrawal_clears_balance_test() {
		account.deposit(AMOUNT * 2);
		account.withdraw(AMOUNT);
		account.withdraw(AMOUNT);
		assertEquals(0.0, account.getBalance());
	}

	@Test
	void zero_withdrawal_leaves_balance_unchanged_test() {
		account.withdraw(0);
		assertEquals(0, account.getBalance());
	}

	@Test
	void withdrawing_full_balance_clears_account_test() {
		account.deposit(AMOUNT);
		account.withdraw(AMOUNT);
		assertEquals(0, account.getBalance());
	}
}
