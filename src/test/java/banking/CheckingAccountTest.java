package banking;

import static banking.BankTest.AMOUNT;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CheckingAccountTest {

	private CheckingAccount account;

	@BeforeEach
	void setup() {
		account = new CheckingAccount("12345678", 0.01);
	}

	@Test
	void initial_balance_of_checking_account_is_zero_test() {
		assertEquals(0, account.getBalance());
	}

	@Test
	void deposit_into_checking_account_test() {
		account.deposit(AMOUNT);
		assertEquals(AMOUNT, account.getBalance());
	}

	@Test
	void deposit_twice_into_checking_account_increases_balance_accordingly_test() {
		account.deposit(AMOUNT);
		account.deposit(AMOUNT);
		assertEquals(AMOUNT * 2, account.getBalance());
	}

	@Test
	void withdraw_from_checking_account_decreases_balance_test() {
		account.deposit(AMOUNT * 2);
		account.withdraw(AMOUNT);
		assertEquals(AMOUNT, account.getBalance());
	}

	@Test
	void withdraw_twice_from_checking_account_results_in_zero_balance_test() {
		account.deposit(AMOUNT * 2);
		account.withdraw(AMOUNT);
		account.withdraw(AMOUNT);
		assertEquals(0.0, account.getBalance());
	}

	@Test
	void withdraw_zero_from_empty_checking_account_does_not_change_balance_test() {
		account.withdraw(0);
		assertEquals(0, account.getBalance());
	}

	@Test
	void withdraw_entire_balance_from_checking_account_sets_balance_to_zero_test() {
		account.deposit(AMOUNT);
		account.withdraw(AMOUNT);
		assertEquals(0, account.getBalance());
	}

}