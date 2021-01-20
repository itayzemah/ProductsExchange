package cloud.data.dal;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import cloud.data.ExchangeEntity;

public interface ExchangeDataAccessRepository extends MongoRepository<ExchangeEntity, String>{

	public List<ExchangeEntity> findAllByUserEmail(String userEmail, Pageable pageReq);
}
