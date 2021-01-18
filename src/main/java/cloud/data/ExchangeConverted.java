package cloud.data;

import org.springframework.stereotype.Component;

import cloud.boundaries.ExchangeBoundary;
import cloud.boundaries.ExtraBoundary;
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
	public ExchangeEntity fromBoundary(ExchangeBoundary entity) {
		ExchangeEntity rv = new ExchangeEntity();
		rv.setBidId(entity.getBidId());
		if(entity.getExtra() != null) {
			rv.setExtra(this.fromBoundary(entity.getExtra()));
		}
		rv.setTimestamp(entity.getTimestamp());
		rv.setUserEmail(entity.getUserEmail());
		return rv;
	}
	
	private @NonNull ExtraEntity fromBoundary(@NonNull ExtraBoundary entity) {
		ExtraEntity rv = new ExtraEntity();
		rv.setMoney(entity.getMoney());
		rv.setMoreInfo(entity.getMoreInfo());
		return rv;
	}

}
