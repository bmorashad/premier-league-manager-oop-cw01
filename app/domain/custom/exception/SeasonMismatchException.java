package domain.custom.exception;

public class SeasonMismatchException extends RuntimeException {
	private static final long serialVersionUID = 87L;

	public SeasonMismatchException(String errorMessage) {
		super(errorMessage);
	}
}
