package com.h2rd.refactoring.usermanagement.controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.h2rd.refactoring.usermanagement.dao.UserDao;
import com.h2rd.refactoring.usermanagement.model.User;
import com.h2rd.refactoring.usermanagement.util.Consts;

@RestController
public class UserController{

	@Autowired
	private UserDao userDao;


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/users")
	public ResponseEntity<Object> getUsers() {

		return new ResponseEntity(userDao.getUsers(), HttpStatus.OK);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/users")
	public ResponseEntity<User> addUser(@Valid @RequestBody User userRequest) {

		if(userRequest.getRoles().isEmpty())
			return new ResponseEntity(Consts.ONE_ROLE_AT_LEAST, HttpStatus.BAD_REQUEST);

		Optional<User> userOptional = userDao.saveUser(userRequest);
		if (!userOptional.isPresent()) 
			return new ResponseEntity(Consts.EMAIL_ALREADY_EXIT + userRequest.getEmail(), HttpStatus.BAD_REQUEST);


		return new ResponseEntity(Consts.CREATE_SUCCESS, HttpStatus.CREATED);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping("/users")
	public ResponseEntity<Object> updateUser(@RequestBody User userRequest) {

		Optional<User> userOptional = userDao.deleteUserByEmail(userRequest.getEmail());
		if (!userOptional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		userDao.saveUser(userRequest);
		return new ResponseEntity(Consts.UPDATE_SUCCESS, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/users/{email:.+}")
	public ResponseEntity<Object> deleteUserByEmail(@Valid @PathVariable String email) {

		Optional<User> userOptional = userDao.deleteUserByEmail(email);
		if (!userOptional.isPresent())
			return ResponseEntity.badRequest().build();
		else
			return new ResponseEntity(Consts.DELETE_SUCCESS, HttpStatus.OK);
	}	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping("/users/delete-by-name/{name}")
	public ResponseEntity<Object> deleteUserByName(@PathVariable String name) {

		Optional<ArrayList<User>> userOptional = userDao.deleteUserByName(name);
		if (!userOptional.isPresent() || userOptional.get().isEmpty()) 
			return ResponseEntity.badRequest().build(); 
		else
			return new ResponseEntity(Consts.DELETE_SUCCESS, HttpStatus.OK); 
	}

	//In our project, email is id of User entity, findUserByEmail give only one or no result
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/users/email/{email:.+}")
	public ResponseEntity<Object> findUserByEmail(@Valid @PathVariable String email) {

		User user = userDao.findUserByEmail(email);
		Optional<User> userOptional = Optional.ofNullable(user);
		if (!userOptional.isPresent()) {
			ResponseEntity.badRequest().build();
		}

		return new ResponseEntity(user, HttpStatus.OK);
	}

	//We make search by name beacause multiple users can have the same name
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping("/users/name/{name}")
	public ResponseEntity<Object> findUserByName(@PathVariable String name) {

		ArrayList<User> users = userDao.findUserByName(name);
		Optional<Object> userListOptional = Optional.ofNullable((Object)users);
		if (!userListOptional.isPresent()) {
			return ResponseEntity.badRequest().build();
		}

		return new ResponseEntity(userListOptional.get(), HttpStatus.OK);    
	}

}
