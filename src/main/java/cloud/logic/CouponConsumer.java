package cloud.logic;

import cloud.boundaries.CouponBoundary;
import cloud.boundaries.ExchangeBoundary;

public interface CouponConsumer {

	ExchangeBoundary setCoupon(ExchangeBoundary bid);

	CouponBoundary getCoupon(String couponId);

}
