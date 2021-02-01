package cloud.logic;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cloud.boundaries.ExchangeBoundary;
import cloud.boundaries.ProductBoundary;
import cloud.data.ExchangeConverted;
import cloud.data.ExchangeEntity;
import cloud.data.dal.ExchangeDataAccessRepository;
import cloud.data.exceptions.BidNotFoundException;
import cloud.data.exceptions.InvalidDataException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeServiceImplementation implements ExchangeService {
	private ProductConsumer productConsumer;
	private ExchangeDataAccessRepository exchangeDAL;
	private InputValidatorService inputValidator;
	private ExchangeConverted converter;

	@Override
	@Transactional(readOnly = true)
	public ExchangeBoundary[] getAll(int page, int size) {
		return exchangeDAL.findAll(PageRequest.of(page, size)).stream().map(converter::toBoundary)
				.map(productConsumer::setProducts).collect(Collectors.toList()).toArray(new ExchangeBoundary[0]);
	}

	@Override
	@Transactional(readOnly = true)
	public ExchangeBoundary getBidById(String bid) {
		ExchangeBoundary withoutProductDetails = this.converter.toBoundary(exchangeDAL.findById(bid)
				.orElseThrow(() -> new BidNotFoundException("A bid with id: " + bid + " not found")));
		ExchangeBoundary withProduct = this.productConsumer.setProducts(withoutProductDetails);
		return withProduct;
	}

	@Override
	@Transactional
	public ExchangeBoundary create(ExchangeBoundary boundary) {
		if(!inputValidator.IsValidEmail(boundary.getUserEmail())) {
			throw new InvalidDataException("Email address isn't valid");
		}
		if(!inputValidator.areValidProducts(boundary)) {
			throw new InvalidDataException("Invalid values for the products.");
		}
		boundary.setBidId(null);
		boundary.setTimestamp(new Date());

		ProductBoundary oldProduct = productConsumer.getProductFromCatalog(boundary.getOldProduct().getId());
		ProductBoundary newProduct = productConsumer.getProductFromCatalog(boundary.getNewProduct().getId());
		ExchangeBoundary rv = this.converter.toBoundary(this.exchangeDAL.save(this.converter.fromBoundary(boundary, null)));

		rv.setNewProduct(newProduct);
		rv.setOldProduct(oldProduct);

		return rv;
	}

	@Override
	@Transactional(readOnly = true)
	public ExchangeBoundary[] searchBy(String search, String value, String minValue, String maxValue, int page,
			int size) {
		if (!search.equals("extraMoney") && (value == null || value.isEmpty())) {
			throw new InvalidDataException("Invalid value to search for");
		}
		switch (search) {
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
				.map(this.converter::toBoundary).map(this.productConsumer::setProducts).collect(Collectors.toList())
				.toArray(new ExchangeBoundary[0]);
	}

	private ExchangeBoundary[] searchByOldProductId(String value, int page, int size) {
		return this.exchangeDAL.findAllByOldProductId(value, PageRequest.of(page, size)).stream()
				.map(this.converter::toBoundary).map(this.productConsumer::setProducts).collect(Collectors.toList())
				.toArray(new ExchangeBoundary[0]);
	}

	private ExchangeBoundary[] searchByNewProductId(String value, int page, int size) {
		return this.exchangeDAL.findAllByNewProductId(value, PageRequest.of(page, size)).stream()
				.map(this.converter::toBoundary).map(this.productConsumer::setProducts).collect(Collectors.toList())
				.toArray(new ExchangeBoundary[0]);
	}

	private ExchangeBoundary[] searchByExtraMoney(String minValue, String maxValue, int page, int size) {
		double minValueAsDouble, maxValueAsDouble;
		try {
			minValueAsDouble = Double.parseDouble(minValue);
			maxValueAsDouble = Double.parseDouble(maxValue);
		} catch (NumberFormatException e) {
			throw new InvalidDataException("Invalid money format");
		}
		return this.exchangeDAL
				.findAllByExtra_MoneyBetween(minValueAsDouble, maxValueAsDouble, PageRequest.of(page, size)).stream()
				.map(this.converter::toBoundary).map(this.productConsumer::setProducts).collect(Collectors.toList())
				.toArray(new ExchangeBoundary[0]);
	}

	@Override
	@Transactional
	public void update(ExchangeBoundary boundary) {
		checkEmailIfItExists(boundary.getUserEmail());
		checkProductsIfTheyExist(boundary);
		ExchangeEntity existExchangeEntity = this.exchangeDAL.findById(boundary.getBidId())
				.orElseThrow(() -> new BidNotFoundException("A bid with id: " + boundary.getBidId() + " not found"));
		this.exchangeDAL.save(this.converter.fromBoundary(boundary, existExchangeEntity));
	}

	@Override
	@Transactional
	public void removeAll() {
		this.exchangeDAL.deleteAll();
	}
	
	private void checkProductsIfTheyExist(ExchangeBoundary boundary) {
		if(boundary.getOldProduct() != null) {
			if(inputValidator.isNullOrEmpty(boundary.getOldProduct().getId())){
				throw new InvalidDataException("Invalid id for the product");
			}
		}
		if(boundary.getNewProduct() != null) {
			if(inputValidator.isNullOrEmpty(boundary.getNewProduct().getId())) {
				throw new InvalidDataException("Invalid id for the product");
			}
		}
	}
	
	private void checkEmailIfItExists(String email) {
		if(email != null) {
			if(!inputValidator.IsValidEmail(email)) {
				throw new InvalidDataException("Email address isn't valid");
			}
		}
	}
}
