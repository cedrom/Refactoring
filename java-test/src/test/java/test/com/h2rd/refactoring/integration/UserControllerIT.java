package test.com.h2rd.refactoring.integration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import test.com.h2rd.refactoring.AbstractTest;

import com.h2rd.refactoring.usermanagement.controllers.UserController;
import com.h2rd.refactoring.usermanagement.dao.UserDao;
import com.h2rd.refactoring.usermanagement.model.User;
import com.h2rd.refactoring.usermanagement.util.Consts;



public class UserControllerIT extends AbstractTest{

	@Autowired
	UserController userResource;

	@Autowired
	UserDao userDao;

	@Test
	public void createUserTest() {

		User integration = new User();
		integration.setName("integration");
		integration.setEmail("initial4@integration.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("dba");
		integration.setRoles(roles);     
		ResponseEntity<User> response = userResource.addUser(integration);
		assertEquals(Consts.CREATED_CODE, response.getStatusCodeValue());

	}

	@Test
	public void createUserFailTest() {

		createUser();
		User integration = new User();
		integration.setName("integration");
		integration.setEmail("initial2@integration.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("dba");
		integration.setRoles(roles);     
		ResponseEntity<User> response = userResource.addUser(integration);
		assertEquals(Consts.BAD_REQUEST_CODE, response.getStatusCodeValue());
		assertEquals(Consts.EMAIL_ALREADY_EXIT + integration.getEmail(), response.getBody());

	}
	
	@Test
	public void updateUserTest() {

		createUser();
		User updated = new User();
		updated.setName("integration updated");
		updated.setEmail("initial2@integration.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("developer");
		updated.setRoles(roles);

		ResponseEntity<Object> response = userResource.updateUser(updated);
		assertEquals(Consts.OK_CODE, response.getStatusCodeValue());
	}
	
	@Test
	public void updateUserFailTest() {

		createUser();
		User updated = new User();
		updated.setName("integration updated");
		updated.setEmail("not-exist-email@integration.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("developer");
		updated.setRoles(roles);

		ResponseEntity<Object> response = userResource.updateUser(updated);
		assertEquals(Consts.BAD_REQUEST_CODE, response.getStatusCodeValue());

	}

	@Test
	public void deleteUserByEmailTest() {

		createUser();
		ResponseEntity<Object> response = userResource.deleteUserByEmail("initial2@integration.com");
		assertEquals(Consts.OK_CODE, response.getStatusCodeValue());
	}

	@Test
	public void deleteUserByNameTest() {

		createUser();
		ResponseEntity<Object> response = userResource.deleteUserByName("integration");
		assertEquals(Consts.OK_CODE, response.getStatusCodeValue());
	}

	@Test
	public void getUsersTest() {

		createUser();
		ResponseEntity<Object> response = userResource.getUsers();
		assertEquals(Consts.OK_CODE, response.getStatusCodeValue());
	}
	
	public void createUser() {

		User integration = new User();
		integration.setName("integration");
		integration.setEmail("initial2@integration.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("dba");
		integration.setRoles(roles);     
		userResource.addUser(integration);
	}


}
