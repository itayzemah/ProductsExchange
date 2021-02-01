package cloud.logic;

import cloud.boundaries.ExchangeBoundary;

public interface InputValidatorService {
	
	public boolean IsValidEmail(String email);
	public boolean areValidProducts(ExchangeBoundary boundary);
	boolean isNullOrEmpty(String string);
}
