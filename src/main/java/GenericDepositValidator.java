public class GenericDepositValidator implements DepositValidator {
	@Override
	public boolean validate(String command) {
		// Splitting the command into parts
		String[] parts = command.split(" ");
		if (parts.length != 4) {
            return false;
        }

		// Extracting the account type from the command
		String accountType = parts[1].toLowerCase();

		// Checking if the account type is one of the expected values
		if (!accountType.equals("checking") && !accountType.equals("savings") && !accountType.equals("cd")) {
			return false; // If it's not, the command is considered invalid
		}

		// Further validation for deposit amount, etc.
		try {
			double amount = Double.parseDouble(parts[3]);
			return amount > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
