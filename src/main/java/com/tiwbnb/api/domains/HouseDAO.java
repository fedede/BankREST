package com.tiwbnb.api.domains;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface HouseDAO extends CrudRepository<House, Long>{
	public List<House> findById(long id);
	public List<House> findByName(String name);
}
