/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server.sessionbean;

import server.entitybean.Customer;

public interface RegistrationBean
{
	void register(String username, String password, String name, String email);

	Customer validateLogin(String username, String password);
}
