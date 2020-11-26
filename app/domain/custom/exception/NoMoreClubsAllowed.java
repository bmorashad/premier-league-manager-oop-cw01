package domain.custom.exception;
public class NoMoreClubsAllowed extends RuntimeException {
	private static final long serialVersionUID = 4L;

	public NoMoreClubsAllowed(String errorMessage) {
		super(errorMessage);
	}
}
