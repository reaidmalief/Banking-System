package banking;

public class SavingsAccount extends Account {

	private boolean hasWithdrawnThisMonth = false;

	public SavingsAccount(String id, double apr) {
		super.id = id;
		super.apr = apr;
		this.balance = 0;
		super.accountType = "Savings";
	}

	@Override
	void setMonths(int months) {
		if (months > super.age) {
			hasWithdrawnThisMonth = false;
		}
		super.age = months;
	}

	boolean getHasWithdrawnThisMonth() {
		return hasWithdrawnThisMonth;
	}

}
