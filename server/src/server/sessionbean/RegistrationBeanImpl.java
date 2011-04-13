/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.sessionbean;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import server.entitybean.Customer;
import server.entitybean.CustomerImpl;

/**
 * 
 * @author Majid Khosravi
 * 
 */
@Stateless(name = "registration")
@Remote(RegistrationBean.class)
@Local
public class RegistrationBeanImpl implements RegistrationBean,
		java.io.Serializable
{

	private static final long serialVersionUID = 1L;

	// Entity manager
	@PersistenceContext
	private EntityManager em;

	// Register a new customer
	@Override
	public void register(String username, String password, String name,
			String email)
	{
		CustomerImpl customer = new CustomerImpl(username, password, name,
				email);
		em.persist(customer);
		System.out.println("Client registered successfully!");
	}

	// validate customer login by username and password
	@Override
	public Customer validateLogin(String username, String password)
	{
		String qry = "from CustomerImpl where username = '" + username + "'";

		// check if the customer exists
		if (em.createQuery(qry).getResultList().isEmpty()) { return null; }

		// get the customer
		Customer customer = (Customer) em.createQuery(qry).getSingleResult();

		// if the pin is correct, return the customer
		if (customer.validatePIN(password)) { return customer; }

		// otherwise return null
		return null;
	}
}
