public class AccountValidator {
	public boolean validate(String command) {
		// splitting the command into parts
		String[] parts = command.split(" ");

		// Checking if command structure matches "create checking <accountNumber>
		return parts.length == 4;
	}
}
