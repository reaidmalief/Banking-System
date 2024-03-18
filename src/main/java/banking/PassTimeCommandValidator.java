package banking;

public class PassTimeCommandValidator {

	PassTimeCommandValidator() {
	}

	boolean validate(String[] commandArguments) {
		return ensureCorrectNumberOfArguments(commandArguments) && ensureIntegerWithinBounds(commandArguments);
	}

	private boolean ensureCorrectNumberOfArguments(String[] commandArguments) {
		return commandArguments.length == 2;
	}

	private int isSecondArgumentInteger(String[] commandArguments) {
		try {
			return Integer.parseInt(commandArguments[1]);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private boolean ensureIntegerWithinBounds(String[] commandArguments) {
		int months = isSecondArgumentInteger(commandArguments);
		return (months >= 1 && months <= 60);
	}

}
