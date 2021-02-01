package cloud.logic;

import org.springframework.stereotype.Service;

import cloud.boundaries.ExchangeBoundary;

@Service
public class InputValidatorServiceImple implements InputValidatorService {
	
	@Override
	public boolean IsValidEmail(String email) {
		return !isNullOrEmpty(email) && email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
	}
	
	@Override
	public boolean areValidProducts(ExchangeBoundary boundary) {
		return doTheProductsExist(boundary) && 
				!isNullOrEmpty(boundary.getOldProduct().getId()) && !isNullOrEmpty(boundary.getNewProduct().getId());
	}
	
	@Override
	public boolean isNullOrEmpty(String string) {
		return string == null || string.trim().isEmpty();
	}
	
	private boolean doTheProductsExist(ExchangeBoundary boundary) {
		return boundary.getOldProduct() != null && boundary.getNewProduct() != null;  
	}

}
