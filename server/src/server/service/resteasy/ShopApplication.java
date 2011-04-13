/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server.service.resteasy;

/**
 *
 * @author Majid Khosravi
 * 
 */
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;

public class ShopApplication extends Application
{
	HashSet<Object> singletons = new HashSet<Object>();

	public ShopApplication()
	{
		singletons.add(new ShopService());
	}

	@Override
	public Set<Class<?>> getClasses()
	{
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		return set;
	}

	@Override
	public Set<Object> getSingletons()
	{
		return singletons;
	}
}
