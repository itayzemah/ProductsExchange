package cloud.boundaries;

import java.util.Date;

import org.springframework.data.annotation.Id;

import cloud.data.ExtraEntity;
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
public class ExchangeBoundary {
	private @Id String bidId;
	private @NonNull String userEmail;
	private @NonNull Date timestamp;
	private @NonNull ProductBoundary oldProduct;
	private @NonNull ProductBoundary newProduct;
	private ExtraBoundary extra;
}
