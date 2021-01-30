package cloud.logic;

import cloud.boundaries.ExchangeBoundary;
import cloud.boundaries.ProductBoundary;

public interface ProductConsumer {

	public ExchangeBoundary setProducts(ExchangeBoundary bid);
	public ProductBoundary getProductFromCatalog(String productId);
}
