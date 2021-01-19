package cloud.logic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cloud.boundaries.ExchangeBoundary;
import cloud.boundaries.ProductBoundary;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
@Getter
@Setter
public class ProductConsumerImple implements ProductConsumer, CommandLineRunner{
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
	public ExchangeBoundary setProduct(ExchangeBoundary bid) {
		ExchangeBoundary rv = bid;
		ProductBoundary newProduct = restTemplate.getForEntity(fullURL+"/" +rv.getNewProduct().getId(), ProductBoundary.class).getBody();
		rv.setNewProduct(newProduct);
		
		ProductBoundary oldProduct = restTemplate.getForEntity(fullURL+"/" +rv.getOldProduct().getId(), ProductBoundary.class).getBody();
		rv.setNewProduct(oldProduct);
		
		return rv;
	}

	@Override
	public void run(String... args) throws Exception {
		System.err.println(this.url + this.port);
		System.err.println(this.fullURL);
	}

}
