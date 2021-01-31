package cloud.data.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import cloud.data.ExchangeEntity;

public interface ExchangeDataAccessRepository extends MongoRepository<ExchangeEntity, String>{

	public List<ExchangeEntity> findAllByUserEmail(String userEmail, Pageable pageReq);
	
	public List<ExchangeEntity> findAllByOldProductId(String id, Pageable pageReq);
	
	public List<ExchangeEntity> findAllByNewProductId(String id, Pageable pageReq);
	
	@Query(value = "{ 'extra_money' : {$gte : ?0, $lte: ?1 }}")
	public List<ExchangeEntity> findAllByExtra_MoneyBetween(Double minValue, Double maxValue, Pageable pageReq);
}
