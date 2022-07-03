package br.com.fta.transaction.infra;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fta.transaction.domain.ImportInfo;

public interface ImportInfoRepository extends MongoRepository<ImportInfo, String> {

	Optional<ImportInfo> findByTransactionsDate(LocalDate date);

}
