package com.tiwbnb.api.domains;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(path="users", rel="users")
public interface UserDAO extends CrudRepository<User, String>{
	
	public Optional<User> findById(Long id);
	public List<User> findByName(String name);
	public User findTop1ByName(String name);
	public List<User> findByNameAndSurname(String name, String surname);
	public List<User> findAll();
	
}
