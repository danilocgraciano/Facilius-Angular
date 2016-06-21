package br.com.facilius.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.facilius.model.User;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity<String>(String.format("{\"reason\":\"%s\"}", e.getMessage()), HttpStatus.NOT_ACCEPTABLE);
	}

	@Autowired
	private UserController controller;

	@RequestMapping(method = RequestMethod.POST)
	public User create(@RequestBody @Valid User user) throws Exception {
		return controller.create(user);
	}

}
