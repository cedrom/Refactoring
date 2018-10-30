package test.com.h2rd.refactoring.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import test.com.h2rd.refactoring.AbstractTest;

import com.h2rd.refactoring.usermanagement.model.User;
import com.h2rd.refactoring.usermanagement.util.Consts;


@AutoConfigureMockMvc
public class UserControllerTest extends AbstractTest {

	@Autowired
	private MockMvc mvc;


	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void getUsersList() throws Exception {

		String uri = "/users";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(Consts.OK_CODE, status);
		String content = mvcResult.getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		ArrayList<User> usersList = super.mapFromJson(content, ArrayList.class);
		assertEquals(usersList.size(), 0);

		User user = new User();
		user.setName("Fake Name");
		user.setEmail("fake@email.com");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		roles.add("master");
		user.setRoles(roles);

	}

	@Test
	public void createUserSuccessTest() throws Exception {
		String uri = "/users";
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		User user = new User();
		user.setEmail("abc100@gmail.com");
		user.setName("Ahmed");
		user.setRoles(roles);
		String inputJson = super.mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(Consts.CREATED_CODE, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, Consts.CREATE_SUCCESS);
	}

	@Test
	public void createUserFailTest() throws Exception {
		createUser();
		String uri = "/users";
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		User user = new User();
		user.setEmail("abc100@gmail.com");
		user.setName("Ahmed");
		user.setRoles(roles);
		String inputJson = super.mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		assertEquals(Consts.BAD_REQUEST_CODE, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, Consts.EMAIL_ALREADY_EXIT + user.getEmail());

	}


	@Test
	public void updateUser() throws Exception {

		createUser();
		String uri = "/users";
		User user = new User();
		user.setEmail("abc100@gmail.com");
		user.setName("name to change");
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		user.setRoles(roles);

		String inputJson = super.mapToJson(user);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		assertEquals(Consts.OK_CODE, mvcResult.getResponse().getStatus());
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, Consts.UPDATE_SUCCESS);
	}

	@Test
	public void deleteUserByEmail() throws Exception {
		createUser();
		String uri = "/users/abc100@gmail.com";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(Consts.OK_CODE, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, Consts.DELETE_SUCCESS);
	}

	@Test
	public void deleteUserByName() throws Exception {
		createUser();
		String uri = "/users/delete-by-name/Ahmed";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(Consts.OK_CODE, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, Consts.DELETE_SUCCESS);
	}

	@Test
	public void getOneElementUsersList() throws Exception {
		createUser();
		String uri = "/users";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(Consts.OK_CODE, status);
		String content = mvcResult.getResponse().getContentAsString();
		@SuppressWarnings("unchecked")
		ArrayList<User> usersList = super.mapFromJson(content, ArrayList.class);
		assertTrue(usersList.size() == 1);
	}

	public void createUser() throws Exception {

		String uri = "/users";
		ArrayList<String> roles = new ArrayList<String>();
		roles.add("admin");
		User user = new User();
		user.setEmail("abc100@gmail.com");
		user.setName("Ahmed");
		user.setRoles(roles);
		String inputJson = super.mapToJson(user);
		mvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

	}


}
