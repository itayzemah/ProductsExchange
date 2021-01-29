package cloud.logic;

import cloud.boundaries.ExchangeBoundary;

public interface ExchangeService {

	public ExchangeBoundary[] getAll(int page, int size);

	public ExchangeBoundary getBidById(String bid);

	public ExchangeBoundary create(ExchangeBoundary boundary);

	public ExchangeBoundary searchBy(String search, String value, String minValue, String maxValue,int page, int size);

	public void update(ExchangeBoundary boundary);

	public void removeAll();

}
