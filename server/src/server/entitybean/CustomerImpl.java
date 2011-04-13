/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.entitybean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Majid Khosravi
 * 
 */
@Entity
@Table(name = "customer")
public class CustomerImpl implements Customer, Serializable
{

	private static final long serialVersionUID = 1L;
	// id column, auto number, primary key
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	// username column
	@Column(name = "username")
	private String username;
	// password column
	@Column(name = "password")
	private String password;
	// client name
	@Column(name = "name")
	private String name;
	// client email
	@Column(name = "email")
	private String email;

	// default constructor
	public CustomerImpl()
	{
	}

	// constructor with arguments, constructs a new client
	public CustomerImpl(String username, String password, String name,
			String email)
	{
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	// verify if given pin is same as customers password
	@Override
	public Boolean validatePIN(String pin)
	{
		if (pin.equals(getPassword())) { return true; }
		return false;
	}

	@Override
	public String toString()
	{
		return "id=" + id + ", username: " + this.getUsername();
	}
}
