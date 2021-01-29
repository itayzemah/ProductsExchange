package cloud.logic;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloud.boundaries.ExchangeBoundary;
import cloud.data.ExchangeConverted;
import cloud.data.dal.ExchangeDataAccessRepository;
import cloud.data.exceptions.BidNotFoundException;
import cloud.data.exceptions.InvalidDataException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeServiceImplementation implements ExchangeService {
	private ProductConsumer productConsumer;
	private ExchangeDataAccessRepository exchangeDAL;
	private ExchangeConverted converter;

	@Override
	@Transactional(readOnly = true)
	public ExchangeBoundary[] getAll(int page, int size) {
		return exchangeDAL.findAll(PageRequest.of(page, size))
				.stream()
				.map(converter::toBoundary)
				.map(productConsumer::setProduct)
				.collect(Collectors.toList()).toArray(new ExchangeBoundary[0]);
	}

	@Override
	@Transactional(readOnly = true)
	public ExchangeBoundary getBidById(String bid) {
		ExchangeBoundary withoutProductDetails =  this.converter.toBoundary(exchangeDAL.findById(bid)
				.orElseThrow(() -> new BidNotFoundException("A bid with id: " + bid + " not found")));
		ExchangeBoundary withProduct = this.productConsumer.setProduct(withoutProductDetails);
		return withProduct;
	}

	@Override
	@Transactional
	public ExchangeBoundary create(ExchangeBoundary boundary) {
		if(boundary.getOldProduct().getId() == null || boundary.getOldProduct().getId().isEmpty() 
				|| boundary.getNewProduct().getId() == null || boundary.getNewProduct().getId().isEmpty()) {
			throw new InvalidDataException("Invalid value for product id");
		}
		return null;
	}
	
	@Override
	@Transactional(readOnly = true)
	public ExchangeBoundary[] searchBy(String search, String value, String minValue, String maxValue,int page, int size) {
		if(value == null || value.isEmpty()) {
			throw new InvalidDataException("Invalid value to search for");
		}
		switch(search) {
		case "user":
			return this.searchByUser(value, page, size);
		case "oldProductId":
			return this.searchByOldProductId(value, page, size);
		case "newProductId":
			return this.searchByNewProductId(value, page, size);
		case "extraMoney":
			return this.searchByExtraMoney(minValue, maxValue, page, size);
		default:
			throw new InvalidDataException("Invalid value to search by");
		}
	}
	
	private ExchangeBoundary[] searchByUser(String value, int page, int size) {
		return this.exchangeDAL.findAllByUserEmail(value, PageRequest.of(page, size)).stream()
				.map(this.converter::toBoundary).collect(Collectors.toList()).toArray(new ExchangeBoundary[0]);
	}
	
	private ExchangeBoundary[] searchByOldProductId(String value, int page, int size) {
		return this.exchangeDAL.findAllByOldProductId(value, PageRequest.of(page, size)).stream()
				.map(this.converter::toBoundary).collect(Collectors.toList()).toArray(new ExchangeBoundary[0]);
	}
	
	private ExchangeBoundary[] searchByNewProductId(String value, int page, int size) {
		return this.exchangeDAL.findAllByNewProductId(value, PageRequest.of(page, size)).stream()
				.map(this.converter::toBoundary).collect(Collectors.toList()).toArray(new ExchangeBoundary[0]);
	}
	
	private ExchangeBoundary[] searchByExtraMoney(String minValue, String maxValue, int page, int size) {
		double minValueAsDouble, maxValueAsDouble;
		try {
			minValueAsDouble = Double.parseDouble(minValue);
			maxValueAsDouble = Double.parseDouble(maxValue);
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Invalid money format");
		}
		return this.exchangeDAL.findAllByExtra_MoneyBetween(minValueAsDouble, maxValueAsDouble, PageRequest.of(page, size))
				.stream().map(this.converter::toBoundary).collect(Collectors.toList()).toArray(new ExchangeBoundary[0]);
	}

	@Override
	@Transactional
	public void update(ExchangeBoundary boundary) {
		if (this.exchangeDAL.existsById(boundary.getBidId())) {
			this.exchangeDAL.save(this.converter.fromBoundary(boundary));
		} else {
			throw new BidNotFoundException("A bid with id: " + boundary.getBidId() + " not found");
		}
	}

	@Override
	@Transactional
	public void removeAll() {
		this.exchangeDAL.deleteAll();
	}
}
