package domain.custom.exception;
public class NoSuchClubException extends RuntimeException {
	private static final long serialVersionUID = 71L;

	public NoSuchClubException(String errorMessage) {
		super(errorMessage);
	}
}
