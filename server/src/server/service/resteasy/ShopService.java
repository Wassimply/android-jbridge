/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package server.service.resteasy;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.bind.annotation.XmlElement;

import server.entitybean.Product;
import server.entitybean.ProductImpl;
import server.entitybean.Products;
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
	public Products getProducts(
			@QueryParam("keyword") final String query)
	{
		String keyword = "";
		if (query != null)
		{
			keyword = query;
		}
		
		Products products = new Products();
		List<ProductImpl> items = new ArrayList<ProductImpl>();
		for(Product product: shop.getProducts(keyword)){
			items.add((ProductImpl) product);
			
		}
		
		products.setProducts(items);

		return products;
	}

	@GET
	@Path("/product/{id}")
	@Produces("application/json")
	public ProductImpl getProduct(@PathParam("id") final int id)
	{

		return (ProductImpl) shop.findProduct(id);
	}
}
