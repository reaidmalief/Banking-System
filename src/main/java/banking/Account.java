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

	public void deposit(double balance) {
		this.balance += balance;
	}

	public void withdraw(double balance) {
		this.balance -= balance;
	}

	public void passTimeAndCalculateAPR(int months) {
		age += months;

		if (accountType.equalsIgnoreCase("cd")) {
			for (int monthLoopIndex = 0; monthLoopIndex < months; monthLoopIndex++) {
				for (int multipleCalculationCounter = 0; multipleCalculationCounter < 4; multipleCalculationCounter++) {
					balance += ((apr / 100) / 12) * balance;
				}
			}
		} else {
			for (int monthLoopIndex = 0; monthLoopIndex < months; monthLoopIndex++) {
				balance += ((apr / 100) / 12) * balance;
			}
		}
	}
}