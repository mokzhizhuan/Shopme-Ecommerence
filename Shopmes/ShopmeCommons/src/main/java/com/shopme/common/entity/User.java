package com.shopme.common.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int ID;
	
	@Column(length = 128, nullable = false, unique = true)
	protected String email;
	
	protected boolean enabled;
	
	@Column(name="first_name", length = 64, nullable = false)
	protected String fname;
	
	@Column(name="last_name", length = 64, nullable = false)
	protected String lname;
	
	@Column(length = 64, nullable = false)
	protected String password;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	protected Set<Role> roles = new HashSet<>();

	
	
	public User() 
	{
		
	}

	public User(String email, String fname, String lname, String password)
	{
		this.email = email;
		this.fname = fname;
		this.lname = lname;
		this.password = password;
		
	}

	public int getID() 
	{
		return ID;
	}

	public void setID(int iD) 
	{
		ID = iD;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public boolean isEnabled() 
	{
		return enabled;
	}

	public void setEnabled(boolean enabled) 
	{
		this.enabled = enabled;
	}

	public String getFname() 
	{
		return fname;
	}

	public void setFname(String fname)
	{
		this.fname = fname;
	}

	public String getLname() 
	{
		return lname;
	}

	public void setLname(String lname) 
	{
		this.lname = lname;
	}

	public String getPassword() 
	{
		return password;
	}

	public void setPassword(String password) 
	{
		this.password = password;
	}

	public Set<Role> getRoles() 
	{
		return roles;
	}

	public void setRoles(Set<Role> roles) 
	{
		this.roles = roles;
	}
	
	public void addRole(Role role)
	{
		this.roles.add(role);
	}

	@Override
	public String toString() {
		return "User [ID=" + ID + ", email=" + email + ", enabled=" + enabled + ", fname=" + fname + ", lname=" + lname
				+ ", password=" + password + ", roles=" + roles + "]";
	}
	
	
}
