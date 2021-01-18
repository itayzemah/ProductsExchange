package cloud.data.exceptions;

public class BidNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BidNotFoundException() {
	}

	public BidNotFoundException(String message) {
		super(message);
	}

	public BidNotFoundException(Throwable cause) {
		super(cause);
	}

	public BidNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public BidNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
