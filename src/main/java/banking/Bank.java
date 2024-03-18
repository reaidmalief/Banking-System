package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bank {

	public final ArrayList<String> accountOrder = new ArrayList<>();
	private final HashMap<String, Account> accounts = new HashMap<>();

	public Bank() {
		// Initialize the state of the Bank object here. This may involve setting up
		// default values,
		// initializing collections, or performing any startup logic required for the
		// Bank instance
		// to function properly. Completing the constructor's implementation is
		// necessary when
		// the object needs to maintain a specific state right from the moment it is
		// created.

	}

	HashMap<String, Account> getAccounts() {
		return accounts;
	}

	public void addAccount(Account account) {
		if (!accounts.containsKey(account.getId())) {
			accounts.put(account.getId(), account);
		}
		accountOrder.add(account.getId());
	}

	public void doDeposit(String id, double balance) {
		if (accounts.containsKey(id) && balance > 0) {
			accounts.get(id).deposit(balance);
		}
	}

	public double doWithdraw(String id, double amount) {
		Account account = accounts.get(id);
		if (account == null) {
			return -1;
		}
		if (account.getBalance() < amount) {
			double previousBalance = account.getBalance();
			account.setBalance();
			return previousBalance;
		} else {
			account.withdraw(amount);
			return amount;
		}
	}

	public void transferBetweenAccounts(String fromAccount, String toAccount, double amount) {
		accounts.get(toAccount).deposit(doWithdraw(fromAccount, amount));
	}

	public void openCheckingAccount(String id, double apr) {
		CheckingAccount account = new CheckingAccount(id, apr);
		addAccount(account);
	}

	void openSavingsAccount(String id, double apr) {
		SavingsAccount account = new SavingsAccount(id, apr);
		addAccount(account);
	}

	public void openCDAccount(String id, double apr, double amount) {
		CDAccount account = new CDAccount(id, apr, amount);
		addAccount(account);
	}

	public void passTime(int months) {
		List<String> accountsToRemove = new ArrayList<>();

		for (String accountID : accounts.keySet()) {
			Account account = accounts.get(accountID);
			if (account.getBalance() == 0) {
				accountsToRemove.add(accountID);
				continue;
			}
			account.passTimeAndCalculateAPR(months);
		}

		for (String accountID : accountsToRemove) {
			accounts.remove(accountID);
			accountOrder.remove(accountID);
		}
	}

	public List<String> getAccountOrder() {
		return accountOrder;
	}
}
