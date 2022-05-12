package br.com.fta.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fta.model.ImportInfo;

public interface ImportInfoRepository extends MongoRepository<ImportInfo, String> {

}
