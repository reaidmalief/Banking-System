package banking;

public class CDAccount extends Account {

	CDAccount(String id, double apr, double initialBalance) {
		super.id = id;
		super.apr = apr;
		this.balance = initialBalance;
		super.accountType = "Cd";
	}

	public Boolean canTransfer() {
		return false;
	}
}
