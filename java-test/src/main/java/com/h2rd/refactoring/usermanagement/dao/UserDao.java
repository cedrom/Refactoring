package com.h2rd.refactoring.usermanagement.dao;

import java.util.ArrayList;
import java.util.Optional;

import com.h2rd.refactoring.usermanagement.model.User;

public interface UserDao {

    public Optional<User> saveUser(User user);
    public ArrayList<User> getUsers();
    public Optional<User> deleteUserByEmail(String email);
    public Optional<ArrayList<User>> deleteUserByName(String name);
    public void updateUser(User userToUpdate);
    public ArrayList<User> findUserByName(String name);
    public User findUserByEmail(String email);

    
}