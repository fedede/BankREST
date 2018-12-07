package com.tiwbnb.api.domains;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TransactionDAO extends CrudRepository<Transaction, Long>{

	public Optional<Transaction> findTop1ById(Long id);
	public List<Transaction> findByHouseId(long id);
	public List<Transaction> findByInvoicedId(long id);
}
