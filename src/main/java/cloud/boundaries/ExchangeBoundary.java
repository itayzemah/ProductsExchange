package cloud.boundaries;

import java.util.Date;
import java.util.Map;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ExchangeBoundary {
	
	private @Id String bidId;
	private @NonNull String userEmail;
	private @NonNull Date timestamp;
	private @NonNull ProductBoundary oldProduct;
	private @NonNull ProductBoundary newProduct;
	private ExtraBoundary extra;
	
	
	public boolean doesProductHaveOnlyId(ProductBoundary product) {
		ObjectMapper jackson = new ObjectMapper();
		Map<String, Object> productAsMap = jackson.convertValue(product, Map.class);
		return productAsMap.containsKey("id") && productAsMap.size() == 1;	
	}
	
	public boolean doesProductHaveDetailsAndHaventId(ProductBoundary product) {
		ObjectMapper jackson = new ObjectMapper();
		Map<String, Object> productAsMap = jackson.convertValue(product, Map.class);
		return !productAsMap.containsKey("id") && productAsMap.size() >= 1;
	}
}
