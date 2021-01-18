package cloud.data;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Data
@Document(collection = "Exchanges")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExchangeEntity {
	private @Id String bidId;
	private @NonNull String userEmail;
	private @NonNull Date timestamp;
	private @NonNull String oldProductId;
	private @NonNull String newProductId;
	private ExtraEntity extra;
}
