package banking;

public abstract class Account {

	private final String id;
	private final double apr;
	protected double balance;

	protected Account(String id, double apr) {
		this.id = id;
		this.apr = apr;
	}

	public String getId() {
		return id;
	}

	public double getBalance() {
		return balance;
	}

	public double getApr() {
		return apr;
	}

	public void deposit(double amount) {
		if (amount > 0) {
			balance += amount;
		}
	}

	public void withdraw(double amount) {
		if (amount > balance) {
			balance = 0;
		} else {
			balance -= amount;
		}
	}

}
