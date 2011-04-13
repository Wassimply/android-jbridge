/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.entitybean;

/**
 * 
 * @author Majid Khosravi
 */
public interface Customer
{

	// persistent attributes
	public Integer getId();

	public void setId(Integer id);

	public String getUsername();

	public String getPassword();

	public String getName();

	public String getEmail();

	// relations
	public Boolean validatePIN(String password);
}
