package br.com.fta.transaction.importinfo;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImportInfoRepository extends MongoRepository<ImportInfo, String> {

	Optional<ImportInfo> findByTransactionsDate(LocalDate date);

}
