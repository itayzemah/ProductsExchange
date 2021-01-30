package cloud.data;      

import org.springframework.stereotype.Component;   

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
		rv.setMoney(entity.getMoney()); 
		rv.setMoreInfo(entity.getMoreInfo()); 
		return rv;  
	}   
	
	
	//*************************************************************************
	//					fromBoundary
	//*************************************************************************
	public ExchangeEntity fromBoundary(ExchangeBoundary boundary) {   
		ExchangeEntity rv = new ExchangeEntity();   
		rv.setBidId(boundary.getBidId());     
		if(boundary.getExtra() != null) {   
			rv.setExtra(this.fromBoundary(boundary.getExtra()));  
		} 
		rv.setOldProductId(boundary.getOldProduct().getId()); 
		rv.setNewProductId(boundary.getNewProduct().getId());
		rv.setTimestamp(boundary.getTimestamp());
		rv.setUserEmail(boundary.getUserEmail());
		return rv; 
	} 
	
	private @NonNull ExtraEntity fromBoundary(@NonNull ExtraBoundary boundary) {
		ExtraEntity rv = new ExtraEntity();
		rv.setMoney(boundary.getMoney());
		rv.setMoreInfo(boundary.getMoreInfo());
		return rv;
	}
}
