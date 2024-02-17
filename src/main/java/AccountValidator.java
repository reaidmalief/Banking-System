public class AccountValidator {
	public boolean validate(String command) {
		// Splitting the command into parts
		String[] parts = command.split(" ");

		// Common validations
		if (parts.length < 3) {
			return false;
		}

		String operation = parts[0].toLowerCase();
		String accountType = parts[1].toLowerCase();
		String accountNumber = parts[2];

		// Operation-specific validation
		switch (operation) {
		case "create":
			return handleCreateOperation(parts);
		case "deposit":
			return handleDepositOperation(parts);
		default:
			return false;
		}
	}

	private boolean handleCreateOperation(String[] parts) {
		if (parts.length < 4 || !parts[2].matches("\\d{8}")) {
			return false;
		}

		switch (parts[1].toLowerCase()) {
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

	private boolean handleDepositOperation(String[] parts) {
		// Check for valid account number and amount
		if (parts.length != 4 || !parts[2].matches("\\d{8}")) {
			return false;
		}
		// Adding check for valid account types for deposit operations
		if (!parts[1].equalsIgnoreCase("checking") && !parts[1].equalsIgnoreCase("savings")
				&& !parts[1].equalsIgnoreCase("cd")) {
			return false;
		}
		// Validate deposit amount
		try {
			double amount = Double.parseDouble(parts[3]);
			return amount > 0;
		} catch (NumberFormatException e) {
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
