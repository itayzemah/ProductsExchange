package cloud.data.exceptions;

public class CouponNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CouponNotFoundException() {
	}

	public CouponNotFoundException(String message) {
		super(message);
	}

	public CouponNotFoundException(Throwable cause) {
		super(cause);
	}

	public CouponNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
