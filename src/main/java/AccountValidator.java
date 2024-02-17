public class AccountValidator {
	public boolean validate(String command) {
		// splitting the command into parts
		String[] parts = command.split(" ");

		// Checking if command structure matches "create checking <accountNumber>
		if (parts.length != 4) {
			return false;
		}

		String operation = parts[0];
		String accountType = parts[1];
		String accountNumber = parts[2];
		String apr = parts[3];

		// validate operation is 'create'
		if (!operation.equals("create")) {
			return false;
		}

		// validate account type is 'checking'
		if (!accountType.equals("checking")) {
			return false;
		}

		// validate account number is 8 digit
		return accountNumber.length() == 8 && accountNumber.matches("\\d+");
	}
}
