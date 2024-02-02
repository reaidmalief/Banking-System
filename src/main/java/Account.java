public abstract class Account {

	protected double balance;
	private String id;
	private double apr;

	public Account(String id, double apr) {
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
