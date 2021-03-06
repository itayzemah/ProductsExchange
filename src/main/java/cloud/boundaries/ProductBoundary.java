package cloud.boundaries;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductBoundary {
	private @NonNull String id;
	private String name;
	private double price;
	private String image;
	private Map<String, Object> productDetails;
	private CategoryBoundary category;
}
