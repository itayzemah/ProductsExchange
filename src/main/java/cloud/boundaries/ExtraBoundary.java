package cloud.boundaries;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ExtraBoundary {
	private double money;
	private CouponBoundary coupon;
	private Map<String, String> moreInfo;
}
