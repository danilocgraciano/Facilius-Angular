package br.com.facilius.controller;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilius.model.User;
import br.com.facilius.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserRepository repository;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<String>(String.format("{\"reason\":\"%s\"}", e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
	}

	@RequestMapping(method = RequestMethod.POST)
	public User create(@RequestBody @Valid User user) throws Exception {

		List<User> list = repository.findByEmail(user.getEmail());
		if (!list.isEmpty()) {
			throw new Exception("A User with email " + user.getEmail() + " already exist!");
		}
		return repository.save(user);
	}

	@RequestMapping(method = RequestMethod.GET, value = "")
	public Iterable<User> list(@RequestParam(value = "data", required = false) String data) throws Exception {

		if (data != null) {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(data, User.class);

			if (user.getEmail() != null && user.getPassword() != null) {
				return repository.findByEmailAndPassword(user.getEmail(), user.getPassword());
			}

			if (user.getEmail() != null) {
				return repository.findByEmail(user.getEmail());
			}

		}
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User get(@PathVariable("id") long id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public User update(@PathVariable("id") long id, @RequestBody @Valid User hotel) {
		return repository.save(hotel);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> delete(@PathVariable("id") long id) {
		repository.delete(id);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}

}
