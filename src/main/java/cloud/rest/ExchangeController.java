package cloud.rest;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cloud.boundaries.ExchangeBoundary;
import cloud.data.exceptions.BidNotFoundException;
import cloud.data.exceptions.ProductNotFoundException;
import cloud.logic.ExchangeService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path="exchange")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ExchangeController {
	
	private ExchangeService exchangeService;
	
	@RequestMapping(method = RequestMethod.GET,produces =  MediaType.APPLICATION_JSON_VALUE)
	public ExchangeBoundary[] getAll(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "1000") int size) {
		
		return this.exchangeService.getAll(page,size);
	}

	@RequestMapping(
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE ,
			produces =  MediaType.APPLICATION_JSON_VALUE)
	public ExchangeBoundary getAll(@RequestBody ExchangeBoundary boundary ) {
		
		return this.exchangeService.create(boundary);
	}
	@RequestMapping(
			method = RequestMethod.PUT,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public void update(@RequestBody ExchangeBoundary boundary ) {
		
		this.exchangeService.update(boundary);
	}
	@RequestMapping(
			method = RequestMethod.DELETE)
	public void removeAll() {
		this.exchangeService.removeAll();
	}

	@RequestMapping(path="/{bid}", method = RequestMethod.GET,produces =  MediaType.APPLICATION_JSON_VALUE)
	public ExchangeBoundary getById(
			@PathVariable String bid) {
		
		return this.exchangeService.getBidById(bid);
	}

	@RequestMapping(path="/find/by", method = RequestMethod.GET,produces =  MediaType.APPLICATION_JSON_VALUE)
	public ExchangeBoundary[] searchBy(
			@RequestParam(name = "search", required = true) String search,
			@RequestParam(name = "value", required = false) String value,
			@RequestParam(name = "minValue", required = false) String minValue,
			@RequestParam(name = "maxValue", required = false) String maxValue,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "1000") int size) {
		
		return this.exchangeService.searchBy(search,value,minValue,maxValue,page,size);
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handleException(ProductNotFoundException e) {
		String error = e.getMessage();
		if (error == null) {
			error = "Not found";
		}
		return Collections.singletonMap("error", error);
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public Map<String, String> handleException(BidNotFoundException e) {
		String error = e.getMessage();
		if (error == null) {
			error = "Not found";
		}
		return Collections.singletonMap("error", error);
	}
	
}
