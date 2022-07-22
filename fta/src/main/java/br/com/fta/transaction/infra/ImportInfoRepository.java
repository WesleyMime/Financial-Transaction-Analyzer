package br.com.fta.transaction.infra;

import java.time.LocalDate;
import java.util.Optional;

import br.com.fta.transaction.domain.ImportInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImportInfoRepository extends MongoRepository<ImportInfo, String> {

	Optional<ImportInfo> findByTransactionsDate(LocalDate date);

}
