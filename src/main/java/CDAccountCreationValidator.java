public class CDAccountCreationValidator implements AccountCreationValidator {
	@Override
	public boolean validate(String command) {
		String[] parts = command.split(" ");
		// Expecting 5 parts: "create", "CD", [accountNumber], [deposit], [APR]
		if (parts.length != 5) {
			return false;
		}

		String operation = parts[0];
		String accountType = parts[1];
		String accountNumber = parts[2];
		String depositString = parts[3];
		String aprString = parts[4];

		// Validate operation and account type
		if (!operation.equals("create") || !accountType.equals("CD")) {
			return false;
		}

		// Validate account number (assuming it should be exactly 8 digits long)
		if (!accountNumber.matches("\\d{8}")) {
			return false;
		}

		// Validate deposit amount as a positive number and meets the minimum
		// requirement
		try {
			double deposit = Double.parseDouble(depositString);
			if (deposit < 1000) { // Assuming $1000 is the minimum deposit for a CD account
				return false;
			}
		} catch (NumberFormatException e) {
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
