public class AccountValidator {
	public boolean validate(String command) {
		// Splitting the command into parts
		String[] parts = command.split(" ");

		// Common validations for all account types
		if (parts.length < 4) {
			return false;
		}

		String operation = parts[0];
		String accountType = parts[1].toLowerCase();
		String accountNumber = parts[2];

		if (!operation.equals("create") || accountNumber.length() != 8 || !accountNumber.matches("\\d+")) {
			return false;
		}

		switch (accountType) {
		case "checking":
		case "savings":
			return validateAPR(parts[3]);
		case "cd":
			if (parts.length != 5) {
				return false;
			}
			return validateCDCommand(parts[3], parts[4]);
		default:
			return false;
		}
	}

	private boolean validateAPR(String apr) {
		try {
			double aprValue = Double.parseDouble(apr);
			return aprValue > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private boolean validateCDCommand(String deposit, String apr) {
		try {
			double depositAmount = Double.parseDouble(deposit);
			double aprValue = Double.parseDouble(apr);
			return depositAmount >= 1000 && aprValue > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
