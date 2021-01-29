package cloud.logic;

import cloud.boundaries.ExchangeBoundary;

public interface ProductConsumer {

	public ExchangeBoundary setProduct(ExchangeBoundary bid);
	public boolean isProductExist(String productId);
}
