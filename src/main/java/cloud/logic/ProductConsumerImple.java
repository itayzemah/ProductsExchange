package cloud.logic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cloud.boundaries.ExchangeBoundary;
import cloud.boundaries.ProductBoundary;
import cloud.data.exceptions.ProductNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
@Getter
@Setter
public class ProductConsumerImple implements ProductConsumer{
	private RestTemplate restTemplate;

	@Value( "${producer.url}" )
	private String url;
	@Value( "${producer.port}" )
	private int port;
	private String fullURL;

	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		fullURL = url+':' +port+"/shopping/products";
	}
	
	@Override
	public ExchangeBoundary setProducts(ExchangeBoundary bid) {
		ExchangeBoundary rv = bid;
		
		ProductBoundary newProduct = getProductFromCatalog(bid.getNewProduct().getId());
		rv.setNewProduct(newProduct);
		
		ProductBoundary oldProduct = getProductFromCatalog(bid.getOldProduct().getId());
		rv.setOldProduct(oldProduct);
		return rv;
	}

	@Override
	public ProductBoundary getProductFromCatalog(String productId) {
		try{
			return restTemplate.getForEntity(fullURL+"/" + productId, ProductBoundary.class).getBody();
		}
		catch (Exception e) {
			throw new ProductNotFoundException("Product " + productId + " not found");
		}
	}
}
