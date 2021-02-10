package cloud.data;      

import org.springframework.stereotype.Component;

import cloud.boundaries.CouponBoundary;
import cloud.boundaries.ExchangeBoundary;  
import cloud.boundaries.ExtraBoundary;
import cloud.boundaries.ProductBoundary;
import lombok.NoArgsConstructor; 
import lombok.NonNull;  

@NoArgsConstructor     
@Component    
public class ExchangeConverted { 

	//*************************************************************************
	// 						to boundary
	//*************************************************************************
	public ExchangeBoundary toBoundary(ExchangeEntity entity) {
		ExchangeBoundary rv = new ExchangeBoundary(); 
		rv.setBidId(entity.getBidId()); 
		if(entity.getExtra() != null) { 
			rv.setExtra(this.toBoundary(entity.getExtra()));
		}
		ProductBoundary oldProduct = new ProductBoundary();
		ProductBoundary newProduct = new ProductBoundary();
		oldProduct.setId(entity.getOldProductId());
		newProduct.setId(entity.getNewProductId());
		rv.setOldProduct(oldProduct);
		rv.setNewProduct(newProduct);
		rv.setTimestamp(entity.getTimestamp());
		rv.setUserEmail(entity.getUserEmail());
		return rv;
	}  

	private @NonNull ExtraBoundary toBoundary(@NonNull ExtraEntity entity) {
		ExtraBoundary rv = new ExtraBoundary();
		CouponBoundary coupon = new CouponBoundary();
		rv.setMoney(entity.getMoney()); 
		rv.setCoupon(coupon);
		rv.setMoreInfo(entity.getMoreInfo()); 
		return rv;  
	}   
	
	
	//*************************************************************************
	//					fromBoundary
	//*************************************************************************
	public ExchangeEntity fromBoundary(ExchangeBoundary boundary, ExchangeEntity existEntity) {   
		ExchangeEntity rv = existEntity != null ? existEntity : new ExchangeEntity();   
		rv.setBidId(boundary.getBidId());     
		if(boundary.getExtra() != null) {   
			rv.setExtra(this.fromBoundary(boundary.getExtra()));  
		}
		if(boundary.getOldProduct() != null && boundary.getOldProduct().getId() != null) {
			rv.setOldProductId(boundary.getOldProduct().getId()); 
		}
		if(boundary.getNewProduct() != null && boundary.getNewProduct().getId() != null) {
			rv.setNewProductId(boundary.getNewProduct().getId());
		}
		if(boundary.getTimestamp() != null) {
			rv.setTimestamp(boundary.getTimestamp());
		}
		if(boundary.getUserEmail() != null) {
			rv.setUserEmail(boundary.getUserEmail());	
		}
		return rv; 
	} 
	
	private @NonNull ExtraEntity fromBoundary(@NonNull ExtraBoundary boundary) {
		ExtraEntity rv = new ExtraEntity();
		rv.setMoney(boundary.getMoney());
		if(boundary.getCoupon() != null && boundary.getCoupon().getCouponId() != null) {
			rv.setCoupon(boundary.getCoupon().getCouponId());
		}
		if(boundary.getMoreInfo() != null) {
			rv.setMoreInfo(boundary.getMoreInfo());
		}
		return rv;
	}
}
