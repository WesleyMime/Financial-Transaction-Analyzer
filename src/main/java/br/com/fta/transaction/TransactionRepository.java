package br.com.fta.transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String>{

	Optional<List<Transaction>> findByDateBetween(LocalDateTime startDay, LocalDateTime endDay);
	
	Optional<List<Transaction>> findByBancoOrigem(String name);
}
