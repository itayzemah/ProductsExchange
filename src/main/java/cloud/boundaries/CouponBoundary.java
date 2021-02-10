package cloud.boundaries;

import java.util.Date;
import java.util.Map;

public class CouponBoundary {
	private String couponId;
	private Integer discount;
	private String productId;
	private Date creationTimestamp;
	private Integer validity;
	private Boolean isUsed;
	private Map<String, Object> moreDetails;

	public CouponBoundary() {
		super();
	}

	public CouponBoundary(String couponId, Integer discount, String productId, Date creationTimestamp, Integer validity,
			Boolean isUsed, Map<String, Object> moreDetails) {
		super();
		this.couponId = couponId;
		this.discount = discount;
		this.productId = productId;
		this.creationTimestamp = creationTimestamp;
		this.validity = validity;
		this.isUsed = isUsed;
		this.moreDetails = moreDetails;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Integer getValidity() {
		return validity;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
	}

	public Boolean getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Boolean isUsed) {
		this.isUsed = isUsed;
	}

	public Map<String, Object> getMoreDetails() {
		return moreDetails;
	}

	public void setMoreDetails(Map<String, Object> moreDetails) {
		this.moreDetails = moreDetails;
	}

	@Override
	public String toString() {
		return "CouponBoundary [couponId=" + couponId + ", discount=" + discount + ", productId=" + productId
				+ ", creationTimestamp=" + creationTimestamp + ", validity=" + validity + ", isUsed=" + isUsed
				+ ", moreDetails=" + moreDetails + "]";
	}

}
