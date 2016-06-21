package br.com.facilius.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.facilius.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
	List<User> findByEmail(String email);
	
	List<User> findByEmailAndPassword(String email, String password);

}
