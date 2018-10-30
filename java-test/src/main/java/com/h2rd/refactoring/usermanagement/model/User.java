package com.h2rd.refactoring.usermanagement.model;

import java.util.ArrayList;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;


public class User {

	@NotEmpty
	@Email
    String email;
	
    String name;
    
    @NotNull
    ArrayList<String> roles;
    
    public User() {

	}

	public User(String email, String name, ArrayList<String> roles) {
		super();
		this.email = email;
		this.name = name;
		this.roles = roles;
	}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public ArrayList<String> getRoles() {
        return roles;
    }
    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
    
    @Override
	public String toString() {
		return String.format(
				"User [email=%s, name=%s, roles=%s]", email, name,
				roles);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	
	public User clone() {
        User usr = new User();
        usr.setEmail(this.email);
        usr.setName(this.name);
        usr.setRoles(this.getRoles());
        return usr;
    }
}
