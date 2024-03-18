package banking;

public abstract class Account {
	String id;
	double apr;
	double balance;
	int age = 0;
	String accountType;

	public String getId() {
		return id;
	}

	public double getBalance() {
		return balance;
	}

	void setBalance() {
		this.balance = 0;
	}

	public double getApr() {
		return apr;
	}

	public int getMonths() {
		return age;
	}

	void setMonths(int months) {
		this.age = months;
	}

	public String getAccountType() {
		return accountType;
	}

	public void deposit(double amount) {
		this.balance += amount;
	}

	public void withdraw(double amount) {
		this.balance -= amount;
	}

	public void passTimeAndCalculateAPR(int months) {
		age += months;
		calculateInterest(months);
	}

	protected void calculateInterest(int months) {
		int calculationsPerMonth = accountType.equalsIgnoreCase("cd") ? 4 : 1;
		for (int month = 0; month < months; month++) {
			for (int calculation = 0; calculation < calculationsPerMonth; calculation++) {
				balance += ((apr / 100) / 12) * balance;
			}
		}
	}
}
