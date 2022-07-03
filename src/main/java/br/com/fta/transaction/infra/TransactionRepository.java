package br.com.fta.transaction.infra;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.fta.transaction.domain.Transaction;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String>{

	Optional<List<Transaction>> findByDateBetween(LocalDateTime startDay, LocalDateTime endDay);
	
	Optional<List<Transaction>> findByOriginBank(String name);
}
