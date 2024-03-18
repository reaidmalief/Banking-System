package banking;

import static banking.BankTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CDAccountTest {

	private CDAccount account;

	@BeforeEach
	void setup() {
		account = new CDAccount(ID, APR, AMOUNT);
	}

	@Test
	void cd_balance_matches_initial_amount_test() {
		assertEquals(AMOUNT, account.getBalance());
	}

	@Test
	void cd_account_can_not_be_transferred() {
		assertFalse(account.canTransfer());
	}
}