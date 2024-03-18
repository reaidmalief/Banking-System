package banking;

import static banking.BankTest.APR;
import static banking.BankTest.ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AccountTest {
	static final int BALANCE = 1000;

	@Test

	void opening_of_checking_account_test() {
		CheckingAccount account = new CheckingAccount(ID, APR);
		assertEquals(ID, account.getId());
		assertEquals(APR, account.getApr());
	}

	@Test
	void creation_of_savings_account_test() {
		SavingsAccount account = new SavingsAccount(ID, APR);
		assertEquals(ID, account.getId());
		assertEquals(APR, account.getApr());
	}

	@Test
	void creation_of_cd_account_test() {
		CDAccount account = new CDAccount(ID, APR, BALANCE);
		assertEquals(ID, account.getId());
		assertEquals(APR, account.getApr());
		assertEquals(BALANCE, account.getBalance());
	}

}