package br.com.facilius.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilius.conf.security.TokenHandler;
import br.com.facilius.model.User;
import br.com.facilius.repository.UserRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	private TokenHandler tokenHandler;

	public LoginController() {
		this.tokenHandler = new TokenHandler();
	}

	@Autowired
	private UserRepository repository;

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<String>(String.format("{\"reason\":\"%s\"}", e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<String> login(@RequestBody String data) throws Exception {

		String token = "";
		if (data != null) {
			ObjectMapper mapper = new ObjectMapper();
			User user = mapper.readValue(data, User.class);

			List<User> list = null;

			if (user.getEmail() != null && user.getPassword() != null) {
				list = repository.findByEmailAndPassword(user.getEmail(), user.getPassword());
			}

			if (!list.isEmpty()) {
				user = list.get(0);
				if (user.getId() != null && user.getId() > 0) {
					token = tokenHandler.create(TokenHandler.APP_WEB_ID, user, 60);
				}

			}else{
				throw new Exception("User not found or unauthorized!");
			}
		}else{
			throw new Exception("Insufficient data!");
		}

		return new ResponseEntity<String>(String.format("{\"token\":\"%s\"}", token), HttpStatus.OK);
	}

}
