import java.util.ArrayList;
import java.util.HashMap;

public class Bank {

	public final ArrayList<String> accountOrder = new ArrayList<>();
	private final HashMap<String, Account> accounts;
	private final ArrayList<String> accountOrder = new ArrayList<>();

	public Bank() {
		accounts = new HashMap<>();
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

	public Account getAccount(String id) {
		return accounts.get(id);
	}

	public int getNumOfAccounts() {
		return accounts.size();
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

	public void openCheckingAccount(String id, double amount) {
		CheckingAccount account = new CheckingAccount(id, amount);
		accounts.put(id, account);
		accountOrder.add(id);
	}

	void openSavingsAccount(String id) {
		SavingsAccount account = new SavingsAccount(id, 0.01);
		accounts.put(id, account);
		accountOrder.add(id);
	}

}
