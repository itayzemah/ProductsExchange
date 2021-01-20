package cloud.logic;

import cloud.boundaries.ExchangeBoundary;
import cloud.boundaries.ProductBoundary;

public interface ProductConsumer {

	public ExchangeBoundary setProduct(ExchangeBoundary bid);
	public ProductBoundary createProduct(ProductBoundary product);
}
