package cloud.logic;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import cloud.boundaries.ExchangeBoundary;
import cloud.data.ExchangeConverted;
import cloud.data.dal.ExchangeDataAccessRepository;
import cloud.data.exceptions.BidNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeServiceImplementation implements ExchangeService {
	private ProductConsumer productConsumer;
	private ExchangeDataAccessRepository exchangeDAL;
	private ExchangeConverted converter;

	@Override
	public ExchangeBoundary[] getAll(int page, int size) {
		return exchangeDAL.findAll(PageRequest.of(page, size))
				.stream()
				.map(converter::toBoundary)
				.map(productConsumer::setProduct)
				.collect(Collectors.toList()).toArray(new ExchangeBoundary[0]);
	}

	@Override
	public ExchangeBoundary getBidById(String bid) {
		ExchangeBoundary withoutProductDetails =  this.converter.toBoundary(exchangeDAL.findById(bid)
				.orElseThrow(() -> new BidNotFoundException("A bid with id: " + bid + " not found")));
		ExchangeBoundary withProduct = this.productConsumer.setProduct(withoutProductDetails);
		return withProduct;
	}

	@Override
	public ExchangeBoundary create(ExchangeBoundary boundary) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeBoundary searchBy(String search, String value, String minValue, String maxValue,int page, int size) {
		// TODO Auto-generated method stub
		this.exchangeDAL.findAllByUserEmail(value, PageRequest.of(page, size));
		return null;
	}

	@Override
	public void update(ExchangeBoundary boundary) {
		if (this.exchangeDAL.existsById(boundary.getBidId())) {
			this.exchangeDAL.save(this.converter.fromBoundary(boundary));
		} else {
			throw new BidNotFoundException("A bid with id: " + boundary.getBidId() + " not found");
		}

	}

	@Override
	public void removeAll() {
		this.exchangeDAL.deleteAll();

	}

}
