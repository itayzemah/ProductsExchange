package cloud.logic;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cloud.boundaries.CouponBoundary;
import cloud.boundaries.ExchangeBoundary;
import cloud.data.exceptions.CouponNotFoundException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@NoArgsConstructor
@Getter
@Setter
public class CouponConsumerImple implements CouponConsumer {
	private RestTemplate restTemplate;
	
	@Value( "${coupon.url}" )
	private String url;
	@Value( "${coupon.port}" )
	private int port;
	private String fullURL;
	
	@PostConstruct
	public void init() {
		this.restTemplate = new RestTemplate();
		fullURL = url + ':' + port + "/coupons";
	}
	
	@Override
	public ExchangeBoundary setCoupon(ExchangeBoundary bid) {
		ExchangeBoundary rv = bid;
		
		if(bid.getExtra().getCoupon() != null) {
			CouponBoundary coupon = getCoupon(bid.getExtra().getCoupon().getCouponId());
			rv.getExtra().setCoupon(coupon);
		}
		
		return rv;
	}

	@Override
	public CouponBoundary getCoupon(String couponId) {
		try{
			if(couponId == null || couponId.trim().isEmpty())
				return null;
			return restTemplate.getForEntity(fullURL+"/" + couponId, CouponBoundary.class).getBody();
		}
		catch (Exception e) {
			throw new CouponNotFoundException("Product " + couponId + " not found");
		}
	}

}
