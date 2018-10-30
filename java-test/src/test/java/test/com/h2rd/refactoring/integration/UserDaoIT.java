package test.com.h2rd.refactoring.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.com.h2rd.refactoring.AbstractTest;

import com.h2rd.refactoring.usermanagement.dao.UserDao;
import com.h2rd.refactoring.usermanagement.model.User;

public class UserDaoIT extends AbstractTest{
	
	@Autowired
	UserDao userDao;

	@Test
	public void saveUserTest() {

		User user = new User();
		user.setName("Fake Name");
		user.setEmail("fake@email.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("master");
		user.setRoles(roles);
		userDao.saveUser(user);

		assertEquals("Fake Name", user.getName());
		assertTrue(userDao.getUsers().size() > 0);

	}

	@Test
	public void updateUserTest() throws UnsupportedEncodingException {

		User user = new User();
		user.setName("Fake Name");
		user.setEmail("fake@email.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("master");
		user.setRoles(roles);

		userDao.saveUser(user);

		User updatedUser = user.clone();
		updatedUser.setName("Fake Name updated");
		roles.add("admin");
		roles.add("dba");
		updatedUser.setRoles(roles);

		userDao.updateUser(updatedUser);

		assertEquals(userDao.findUserByEmail("fake@email.com").getName(), "Fake Name updated");

	}

	@Test
	public void deleteUserByEmailTest() throws UnsupportedEncodingException {

		User user = new User();
		user.setName("Fake Name");
		user.setEmail("fake@email.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("master");
		user.setRoles(roles);
		userDao.saveUser(user);
		userDao.deleteUserByEmail(user.getEmail());		
		assertEquals(userDao.findUserByEmail("fake@email.com"), null);

	}
	
	@Test
	public void deleteUserByEmailFailTest() {

		Optional<User> optionaluser = userDao.deleteUserByEmail("email-that-not-exist@gmail.com");
		assertEquals(optionaluser, Optional.empty());

	}

	@Test
	public void deleteUserByNameTest() {

		User user = new User();
		user.setName("Fake Name");
		user.setEmail("fake@email.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("master");
		user.setRoles(roles);
		userDao.saveUser(user);
		
		User otherUser = user.clone();
		otherUser.setEmail("other-fake@email.com");
		userDao.saveUser(otherUser);

		Optional<ArrayList<User>> optionalusers = userDao.deleteUserByName(user.getName());
		assertEquals(optionalusers.get().size(), 2);

	}
	
	@Test
	public void deleteUserByNameFailTest() {

		Optional<ArrayList<User>> optionalusers = userDao.deleteUserByName("example of name that not exist");
		assertEquals(optionalusers.get().size(), 0);

	}

	@Test
	public void findUserByEmailTest() throws UnsupportedEncodingException {
		User user = new User();
		user.setName("Fake Name");
		user.setEmail("fake@email.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("master");
		user.setRoles(roles);
		userDao.saveUser(user);

		User findUser = userDao.findUserByEmail("fake@email.com");
		assertEquals(findUser.getName(),  "Fake Name");
		assertEquals(findUser.getEmail(),  "fake@email.com");
		assertEquals(findUser.getRoles(),  roles);

	}

	@Test
	public void findUserByNameTest() {
		User user = new User();
		user.setName("Marcello");
		user.setEmail("marcello@email.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("master");
		user.setRoles(roles);
		userDao.saveUser(user);
		User user2 = user.clone();
		user2.setEmail("Ronaldo@email.com");
		userDao.saveUser(user2);

		ArrayList<User> findedUsers = userDao.findUserByName("Marcello");
		assertEquals(findedUsers.size(), 2);
	}

}
