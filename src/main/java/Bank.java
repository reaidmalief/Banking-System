import java.util.HashMap;

public class Bank {

	private final HashMap<String, Account> accounts;

	public Bank() {
		accounts = new HashMap<>();
	}

	public void addAccount(Account account) {
		accounts.put(account.getId(), account);
	}

	public int getNumOfAccounts() {
		return accounts.size();
	}

	public Account getAccount(String id) {
		return accounts.get(id);
	}

	public void deposit(String id, double amount) {
		if (accounts.containsKey(id) && amount > 0) {
			accounts.get(id).deposit(amount);
		}
	}

	public void withdraw(String id, double amount) {
		if (accounts.containsKey(id) && amount > 0) {
			accounts.get(id).withdraw(amount);
		}
	}
}
