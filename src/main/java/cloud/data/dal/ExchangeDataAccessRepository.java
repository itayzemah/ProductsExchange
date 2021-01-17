package cloud.data.dal;

import org.springframework.data.mongodb.repository.MongoRepository;

import cloud.data.ExchangeEntity;

public interface ExchangeDataAccessRepository extends MongoRepository<ExchangeEntity, String>{

}
