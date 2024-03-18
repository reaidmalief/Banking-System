package banking;

public class CheckingAccount extends Account {

	public CheckingAccount(String id, double apr) {
		super.id = id;
		super.apr = apr;
		this.balance = 0;
		super.accountType = "Checking";

	}
}
