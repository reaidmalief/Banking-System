package banking;

import static banking.AccountTest.BALANCE;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BankTest {

	static final String ID = "12345678";
	static final String ID_DIFFERENT = "12345677";
	static final double APR = 0.01;
	static final double AMOUNT = 1000;
	private static final double APR_TWO = 0.60;
	private Bank bank;

	@BeforeEach
	void setUp() {
		bank = new Bank();
	}

	@Test
	void bank_starts_with_no_accounts_test() {
		assertTrue(bank.getAccounts().isEmpty());
	}

	@Test
	void opening_checking_account_test() {
		bank.openCheckingAccount(ID, APR);
		assertEquals(0, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void opening_savings_account_test() {
		bank.openSavingsAccount(ID, APR);
		assertEquals(0, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void opening_cd_account_test() {
		bank.openCDAccount(ID, APR, BALANCE);
		assertEquals(1000, bank.getAccounts().get(ID).getBalance());
	}

	@Test
	void opening_multiple_accounts_test() {
		bank.openCheckingAccount(ID, APR);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		assertEquals(0, bank.getAccounts().get(ID_DIFFERENT).getBalance());
	}

	@Test
	void depositing_into_account_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, AMOUNT);
		Account account = bank.getAccounts().get(ID);
		assertEquals(AMOUNT, account.getBalance());
	}

	@Test
	void double_deposit_into_single_account_test() {
		bank.openSavingsAccount(ID, APR);
		bank.doDeposit(ID, AMOUNT);
		bank.doDeposit(ID, 1);
		Account account = bank.getAccounts().get(ID);
		assertEquals(AMOUNT + 1, account.getBalance());
	}

	@Test
	void withdrawing_from_account_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, AMOUNT * 2);
		bank.doWithdraw(ID, AMOUNT);
		Account account = bank.getAccounts().get(ID);
		assertEquals((AMOUNT * 2) - AMOUNT, account.getBalance());
	}

	@Test
	void double_withdraw_from_single_account_test() {
		bank.openSavingsAccount(ID, APR);
		bank.doDeposit(ID, AMOUNT + 1);
		bank.doWithdraw(ID, AMOUNT);
		bank.doWithdraw(ID, 1);
		Account account = bank.getAccounts().get(ID);
		assertEquals(0.0, account.getBalance());
	}

	@Test
	void withdrawal_exceeding_balance_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, AMOUNT);
		bank.doWithdraw(ID, AMOUNT + 1);
		Account account = bank.getAccounts().get(ID);
		assertEquals(0, account.getBalance());
	}

	@Test
	void transfer_between_checking_accounts_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, AMOUNT);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		bank.transferBetweenAccounts(ID, ID_DIFFERENT, AMOUNT);
		Account firstAccount = bank.getAccounts().get(ID);
		Account secondAccount = bank.getAccounts().get(ID_DIFFERENT);
		assertEquals(0, firstAccount.getBalance());
		assertEquals(1000, secondAccount.getBalance());
	}

	@Test
	void transfer_between_savings_accounts_test() {
		bank.openSavingsAccount(ID, APR);
		bank.doDeposit(ID, 5000);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		bank.transferBetweenAccounts(ID, ID_DIFFERENT, 5000);
		Account firstAccount = bank.getAccounts().get(ID);
		Account secondAccount = bank.getAccounts().get(ID_DIFFERENT);
		assertEquals(0, firstAccount.getBalance());
		assertEquals(5000, secondAccount.getBalance());
	}

	@Test
	void transfer_from_checking_to_savings_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, AMOUNT);
		bank.openSavingsAccount(ID_DIFFERENT, APR);
		bank.transferBetweenAccounts(ID, ID_DIFFERENT, AMOUNT);
		Account checkingAccount = bank.getAccounts().get(ID);
		Account savingsAccount = bank.getAccounts().get(ID_DIFFERENT);
		assertEquals(0, checkingAccount.getBalance());
		assertEquals(1000, savingsAccount.getBalance());
	}

	@Test
	void multiple_transfers_between_accounts_test() {
		bank.openSavingsAccount(ID, APR);
		bank.doDeposit(ID, 5000);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		bank.transferBetweenAccounts(ID, ID_DIFFERENT, 1000);
		Account savingsAccount = bank.getAccounts().get(ID);
		Account checkingAccount = bank.getAccounts().get(ID_DIFFERENT);
		assertEquals(4000, savingsAccount.getBalance());
		assertEquals(1000, checkingAccount.getBalance());
		bank.transferBetweenAccounts(ID, ID_DIFFERENT, 2000);
		assertEquals(2000, savingsAccount.getBalance());
		assertEquals(3000, checkingAccount.getBalance());
	}

	@Test
	void apr_calculation_after_two_months_test() {
		bank.openCheckingAccount(ID, APR_TWO);
		bank.doDeposit(ID, 5000);
		bank.openSavingsAccount(ID_DIFFERENT, APR_TWO);
		bank.openCDAccount(ID_DIFFERENT + "1", APR_TWO + 1, 2000);
		bank.passTime(2);
		assertEquals(5005.00, Math.round(bank.getAccounts().get(ID).getBalance() * 100.0) / 100.0);
		assertNull(bank.getAccounts().get(ID_DIFFERENT));
		assertEquals(2021.43, Math.round(bank.getAccounts().get(ID_DIFFERENT + "1").getBalance() * 100.0) / 100.0);
	}

	@Test
	void passing_two_months_with_hundred_dollar_balance_test() {
		bank.openSavingsAccount(ID, APR_TWO);
		bank.doDeposit(ID, 100);
		bank.passTime(1);
		assertEquals(100.05, Math.round(bank.getAccounts().get(ID).getBalance() * 100.0) / 100.0);
	}

	@Test
	void transfer_amount_exceeding_current_balance_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, 500);
		bank.openCheckingAccount(ID_DIFFERENT, APR);
		bank.transferBetweenAccounts(ID, ID_DIFFERENT, 1000);
		Account firstAccount = bank.getAccounts().get(ID);
		Account secondAccount = bank.getAccounts().get(ID_DIFFERENT);
		assertEquals(0, firstAccount.getBalance());
		assertEquals(500, secondAccount.getBalance());
	}

	@Test
	void passing_one_month_updates_month_counter_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, 1000);
		bank.passTime(1);
		assertEquals(1, bank.getAccounts().get(ID).getMonths());
	}

	@Test
	void passing_twelve_months_updates_month_counter_test() {
		bank.openCheckingAccount(ID, APR);
		bank.doDeposit(ID, 1000);
		bank.passTime(12);
		assertEquals(12, bank.getAccounts().get(ID).getMonths());
	}

	@Test
	void apr_calculation_after_one_month_test() {
		bank.openCheckingAccount(ID, APR_TWO);
		bank.doDeposit(ID, 5000);
		bank.openSavingsAccount(ID_DIFFERENT, APR_TWO);
		bank.doDeposit(ID_DIFFERENT, 80);
		bank.openCDAccount(ID_DIFFERENT + "1", APR, 1000);
		bank.doWithdraw(ID_DIFFERENT + "1", 1000);
		bank.passTime(1);
		Account actualOne = bank.getAccounts().get(ID);
		Account actualTwo = bank.getAccounts().get(ID_DIFFERENT);
		Account actualThree = bank.getAccounts().get(ID_DIFFERENT + "1");
		assertEquals(5002.50, Math.round(actualOne.getBalance() * 100.0) / 100.0);
		assertEquals(80.04, Math.round(actualTwo.getBalance() * 100.0) / 100.0);
		assertNull(actualThree);
	}

}