package com.h2rd.refactoring.usermanagement.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.h2rd.refactoring.usermanagement.model.User;

@Repository
public class UserDaoImpl implements UserDao{


	//Because we not use yet data base that I initialyze an example of users to use it in the rest of my API
	public static ArrayList<User> users = new ArrayList<User>();		
	


	public ArrayList<User> getUsers() {
		return users;
	}


	public Optional<User> saveUser(User user) {
		for(User currentUser : users){
			if(currentUser.getEmail().equals(user.getEmail()) || user.getRoles().isEmpty())
				return Optional.ofNullable(null);
			
		}
		users.add(user);			
		return Optional.ofNullable(user);

	}

	public void updateUser(User userToUpdate) {
		Iterator<User> iter = users.iterator();	
		while(iter.hasNext()) {
			User currentUser = iter.next();
			if(currentUser.getEmail().equals(userToUpdate.getEmail())) {
				currentUser.setName(userToUpdate.getName());
				currentUser.setRoles(userToUpdate.getRoles());
			}
		}

	}

	public Optional<ArrayList<User>> deleteUserByName(String name) {
		ArrayList<User> matchedUsers = new ArrayList<User>();
		Iterator<User> iter = users.iterator();
		while(iter.hasNext()) {
			User currentUser = iter.next();
			if(currentUser.getName().equals(name)) {
				matchedUsers.add(currentUser);
				iter.remove();  
			}
		}

		//return matchedUsers.isEmpty() ? null : Optional.ofNullable(matchedUsers);
		return Optional.ofNullable(matchedUsers);

	}


	public Optional<User> deleteUserByEmail(String email) {
		User userToDelete = null;	
		Iterator<User> iter = users.iterator();
		while(iter.hasNext()) {
			User currentUser = iter.next();
			if(currentUser.getEmail().equals(email)) {
				userToDelete = currentUser;
				iter.remove(); 
				break;
			}
		}

		return Optional.ofNullable(userToDelete);

	}

	public ArrayList<User> findUserByName(String name) {
		ArrayList<User> matchedUsers = new ArrayList<User>();
		for (User user : users) {
			if (user.getName().equals(name)) {
				matchedUsers.add(user);
			}
		}

		return matchedUsers;
	}

	public User findUserByEmail(String email) {

		User found = null;		
		for (User user : users) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				found =  user; 
				break;
			}
		}
		
		return found;
	}

}
