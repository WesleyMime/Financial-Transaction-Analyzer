package br.com.fta.transaction.importinfo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImportInfoRepository extends MongoRepository<ImportInfo, String> {

}
