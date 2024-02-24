public class SavingsAccountCreationValidator implements AccountCreationValidator {
	@Override
	public boolean validate(String command) {
		String[] parts = command.split(" ");
		// Expecting 4 parts: "create", "savings", [accountNumber], [APR]
		if (parts.length != 4) {
			return false;
		}

		String operation = parts[0];
		String accountType = parts[1];
		String accountNumber = parts[2];
		String aprString = parts[3];

		// Validate operation and account type
		if (!operation.equals("create") || !accountType.equals("savings")) {
			return false;
		}

		// Validate account number (assuming it should be exactly 8 digits long)
		if (!accountNumber.matches("\\d{8}")) {
			return false;
		}

		// Validate APR as a positive double
		try {
			double apr = Double.parseDouble(aprString);
			if (apr <= 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true; // Passed all validations
	}

}
