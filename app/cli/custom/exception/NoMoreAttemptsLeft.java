package cli.custom.exception;
public class NoMoreAttemptsLeft extends RuntimeException {
	private static final long serialVersionUID = 3L;
	public NoMoreAttemptsLeft(String errorMsg) {
		super(errorMsg);
	}
}
