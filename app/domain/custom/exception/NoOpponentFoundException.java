package domain.custom.exception;
public class NoOpponentFoundException extends RuntimeException {
	private static final long serialVersionUID = 91L;
	public NoOpponentFoundException(String errorMessage) {
		super(errorMessage);
	}
}
