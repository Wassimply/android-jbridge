/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server.service.resteasy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import server.entitybean.Product;
import server.sessionbean.ShopBean;

/**
 * 
 * @author Majid Khosravi
 * 
 */
@Path("/RestEasy")
public class ShopService
{

	private InitialContext context = null;
	private ShopBean shop = null;

	public ShopService()
	{

		try
		{
			context = new InitialContext();
			shop = (ShopBean) context.lookup("jbridge/shop/local");
		}
		catch (NamingException ex)
		{
		}
	}

	@GET
	@Path("/products")
	@Produces("application/json")
	public Collection<Product> getProducts(
			@QueryParam("keyword") final String query)
	{
		String keyword = "";
		if (query != null)
		{
			keyword = query;
		}
		Collection<Product> products = shop.getProducts(keyword);

		return products;
	}

	@GET
	@Path("/product/{id}")
	@Produces("application/json")
	public List<Product> getProduct(@PathParam("id") final int id)
	{

		List<Product> products = new ArrayList<Product>();
		products.add(shop.findProduct(id));

		return products;
	}
}
